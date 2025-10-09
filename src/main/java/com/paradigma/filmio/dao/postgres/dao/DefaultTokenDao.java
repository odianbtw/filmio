package com.paradigma.filmio.dao.postgres.dao;

import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.domain.model.UserStatus;
import com.paradigma.filmio.core.exception.NotFoundException;
import com.paradigma.filmio.core.port.out.TokenDao;
import com.paradigma.filmio.dao.postgres.model.EmailVerificationToken;
import com.paradigma.filmio.dao.postgres.model.PasswordReset;
import com.paradigma.filmio.dao.postgres.spring.repository.EmailVerificationRepository;
import com.paradigma.filmio.dao.postgres.spring.repository.PasswordResetRepository;
import com.paradigma.filmio.dao.postgres.spring.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DefaultTokenDao implements TokenDao {

    private final EmailVerificationRepository emailVerificationRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordResetRepository passwordResetRepository;

    @Override
    public void createToken(UserAccount userAccount, String token) {
        final EmailVerificationToken entity = EmailVerificationToken.builder()
                .id(UUID.randomUUID())
                .userAccountId(userAccount.getId())
                .token(token)
                .used(false)
                .expiresAt(Instant.now().plusSeconds(3600))
                .createdAt(Instant.now())
                .build();
        emailVerificationRepository.save(entity);
    }

    @Override
    public void verifyUserByToken(String token) {
        final var entity = emailVerificationRepository
                .findValidToken(token, Instant.now())
                .orElseThrow(() -> new NotFoundException("Couldn't verify any user with provided token."));
        final var userEntity = userAccountRepository.findById(entity.getUserAccountId()).get();
        userEntity.setStatus(UserStatus.ACTIVE);
        entity.setUsed(true);
    }

    @Override
    public void createPasswordResetToken(UserAccount userAccount, String token) {
        final PasswordReset entity = PasswordReset.builder()
                .id(UUID.randomUUID())
                .userAccountId(userAccount.getId())
                .token(token)
                .used(false)
                .expiresAt(Instant.now().plusSeconds(3600))
                .createdAt(Instant.now())
                .build();
        passwordResetRepository.save(entity);
    }

    @Override
    public UUID findUserIdByPasswordResetToken(String token) {
        final var entity = passwordResetRepository
                .findValidToken(token, Instant.now())
                .orElseThrow(() -> new NotFoundException("Couldn't reset user's password with provided token."));
        return entity.getUserAccountId();
    }

}
