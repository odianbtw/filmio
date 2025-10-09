package com.paradigma.filmio.dao.postgres.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "password_reset")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordReset {
    @Id
    private UUID id;
    @Column(name = "user_account_id")
    private UUID userAccountId;
    private String token;
    @Column(name = "expires_at")
    private Instant expiresAt;
    private boolean used;
    @Column(name = "created_at")
    private Instant createdAt;
}
