package com.paradigma.filmio.dao.postgres.model;


import com.paradigma.filmio.core.domain.model.UserRole;
import com.paradigma.filmio.core.domain.model.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "user_account")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccountEntity {
    @Id
    private UUID id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "user_role")
    private UserRole role;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "user_status")
    private UserStatus status;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
}
