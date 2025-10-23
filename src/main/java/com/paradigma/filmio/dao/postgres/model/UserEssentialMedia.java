package com.paradigma.filmio.dao.postgres.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_essential_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEssentialMedia {
    @Id
    @Column(name = "user_id")
    private UUID userId;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id")
    private MediaEntity avatar;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "backdrop_id")
    private MediaEntity backdrop;
}
