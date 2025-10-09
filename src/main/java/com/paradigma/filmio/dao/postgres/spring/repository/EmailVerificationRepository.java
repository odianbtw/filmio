package com.paradigma.filmio.dao.postgres.spring.repository;

import com.paradigma.filmio.dao.postgres.model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationRepository extends JpaRepository<EmailVerificationToken, UUID> {
    @Query("""
    from EmailVerificationToken evt
    where evt.used = false
    and evt.expiresAt > :now
    and evt.token = :token
    """)
    Optional<EmailVerificationToken> findValidToken(@Param("token") String token,
                                                    @Param("now")Instant now);
}
