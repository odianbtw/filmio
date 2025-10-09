package com.paradigma.filmio.dao.postgres.dao;

import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.port.out.UserDao;
import com.paradigma.filmio.dao.postgres.mapper.UserEntityMapper;
import com.paradigma.filmio.dao.postgres.spring.repository.UserAccountRepository;
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
        return userEntityMapper.toUserAccount(saved);
    }

    @Override
    public Optional<UserAccount> findById(UUID id) {
        final var user = userAccountRepository.findById(id);
        return user.map(userEntityMapper::toUserAccount);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        final var entity = userAccountRepository.findByEmail(email);
        return entity.map(userEntityMapper::toUserAccount);
    }

    @Override
    public void update(UserAccount userAccount) {
        final var entity = userEntityMapper.toUserAccountEntity(userAccount);
        userAccountRepository.save(entity);
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
