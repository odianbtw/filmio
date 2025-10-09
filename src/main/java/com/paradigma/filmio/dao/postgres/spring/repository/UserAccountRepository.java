package com.paradigma.filmio.dao.postgres.spring.repository;

import com.paradigma.filmio.dao.postgres.model.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, UUID> {
    Optional<UserAccountEntity> findByEmail(String email);
    Optional<UserAccountEntity> findByUsername(String username);
}
