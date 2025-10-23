package com.paradigma.filmio.dao.postgres.dao;

import com.paradigma.filmio.core.domain.model.User;
import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.port.out.UserDao;
import com.paradigma.filmio.dao.postgres.mapper.UserEntityMapper;
import com.paradigma.filmio.dao.postgres.model.UserAccountEntity;
import com.paradigma.filmio.dao.postgres.model.UserEntity;
import com.paradigma.filmio.dao.postgres.model.UserEssentialMedia;
import com.paradigma.filmio.dao.postgres.spring.repository.UserAccountRepository;
import com.paradigma.filmio.dao.postgres.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DefaultUserDao implements UserDao {

    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAccount create(UserAccount userAccount) {
        final var entity = userEntityMapper.toUserAccountEntity(userAccount);
        final var encodedPassword = passwordEncoder.encode(
                entity.getPassword()
        );
        entity.setPassword(encodedPassword);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        final var saved = userAccountRepository.save(entity);
        createUserDetail(saved);
        return userEntityMapper.toUserAccount(saved);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public Optional<User> findById(UUID id) {
        final var user = userRepository.findByIdWithMedias(id);
        return user.map(userEntityMapper::toUser);
    }


    private void createUserDetail(UserAccountEntity userAccount) {
        final var user = UserEntity.builder()
                .userAccount(userAccount)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        final var saved = userRepository.save(user);
        final var essentialMedia = new UserEssentialMedia();
        essentialMedia.setUser(saved);
        saved.setUserEssentialMedia(essentialMedia);
    }


//    @Override
//    public Optional<UserAccount> findById(UUID id) {
//        final var user = userAccountRepository.findById(id);
//        return user.map(userEntityMapper::toUserAccount);
//    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        final var entity = userAccountRepository.findByEmail(email);
        return entity.map(userEntityMapper::toUserAccount);
    }


    @Override
    public void changeUserPassword(UUID id, String newPassword) {
        final var user = userAccountRepository.findById(id).get();
        final var encodedPassword = passwordEncoder.encode(
                newPassword
        );
        user.setPassword(encodedPassword);
    }

    @Override
    public Optional<UserAccount> findByUsernameAndPassword(String username, String password) {
        final var user = userAccountRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.map(userEntityMapper::toUserAccount);
        } else {
            return Optional.empty();
        }
    }

}
