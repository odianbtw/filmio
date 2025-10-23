package com.paradigma.filmio.dao.postgres.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "user_following")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserFollowing.UserFollowingId.class)
public class UserFollowing {

    @Id
    @ManyToOne
    @JoinColumn(name = "follower_user_id")
    private UserEntity follower;

    @Id
    @ManyToOne
    @JoinColumn(name = "followed_user_id")
    private UserEntity followed;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserFollowingId implements Serializable {
        private UUID follower;
        private UUID followed;
    }
}
