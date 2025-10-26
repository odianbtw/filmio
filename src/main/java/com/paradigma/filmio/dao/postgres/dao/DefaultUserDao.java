package com.paradigma.filmio.dao.postgres.dao;

import com.paradigma.filmio.core.domain.model.User;
import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.exception.DataConflictException;
import com.paradigma.filmio.core.exception.NotFoundException;
import com.paradigma.filmio.core.model.UserSocialStatistics;
import com.paradigma.filmio.core.port.out.UserDao;
import com.paradigma.filmio.dao.postgres.mapper.MediaEntityMapper;
import com.paradigma.filmio.dao.postgres.mapper.UserEntityMapper;
import com.paradigma.filmio.dao.postgres.model.UserAccountEntity;
import com.paradigma.filmio.dao.postgres.model.UserEntity;
import com.paradigma.filmio.dao.postgres.model.UserEssentialMedia;
import com.paradigma.filmio.dao.postgres.spring.repository.CountryRepository;
import com.paradigma.filmio.dao.postgres.spring.repository.UserAccountRepository;
import com.paradigma.filmio.dao.postgres.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DefaultUserDao implements UserDao {

    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;
    private final UserEntityMapper userMapper;
    private final MediaEntityMapper mediaMapper;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAccount create(UserAccount userAccount) {
        final var entity = userMapper.toUserAccountEntity(userAccount);
        final var encodedPassword = passwordEncoder.encode(
                entity.getPassword()
        );
        entity.setPassword(encodedPassword);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        final var saved = userAccountRepository.save(entity);
        createUserDetail(saved);
        return userMapper.toUserAccount(saved);
    }

    @Override
    public void update(User user) {
        final var userEntity = userRepository.findByIdWithMedias(user.getId())
                .orElseThrow(() -> new NotFoundException("Cannot find user with provided id."));
        userEntity.getUserAccount().setUsername(user.getUsername());
        userEntity.setDescription(user.getDescription());
        if (user.getCountry() != null) {
            final var country = countryRepository.findById(user.getCountry().getId())
                    .orElseThrow(() -> new DataConflictException("Provided country id doesn't exists."));
            userEntity.setCountry(country);
        }
        userEntity.getUserEssentialMedia().setAvatar(
                mediaMapper.toMediaEntity(user.getAvatar().orElse(null))
        );
        userEntity.getUserEssentialMedia().setBackdrop(
                mediaMapper.toMediaEntity(user.getBackdrop().orElse(null))
        );
        userEntity.setUpdatedAt(Instant.now());
    }

    @Override
    public UserSocialStatistics findStatisticsById(UUID id) {
        return userRepository.findStatisticsById(id);
    }

    @Override
    public Optional<User> findById(UUID id) {
        final var user = userRepository.findByIdWithMedias(id);
        return user.map(userMapper::toUser);
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

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        final var entity = userAccountRepository.findByEmail(email);
        return entity.map(userMapper::toUserAccount);
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
            return user.map(userMapper::toUserAccount);
        } else {
            return Optional.empty();
        }
    }

}
