package com.paradigma.filmio.dao.postgres.spring.repository;

import com.paradigma.filmio.dao.postgres.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {
    @Query("""
    from PasswordReset pr where 
    pr.used = false 
    and pr.expiresAt > :now
    and pr.token = :token
    """)
    Optional<PasswordReset> findValidToken(@Param("token") String token,
                                           @Param("now")Instant now);
}
