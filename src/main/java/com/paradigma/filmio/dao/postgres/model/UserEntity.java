package com.paradigma.filmio.dao.postgres.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private UUID userId;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private UserAccountEntity userAccount;
    private String description;
    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    private UserEssentialMedia userEssentialMedia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private CountryEntity country;
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private Set<UserFollowing> following;
    @OneToMany(mappedBy = "followed", fetch = FetchType.LAZY)
    private Set<UserFollowing> followers;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
}
