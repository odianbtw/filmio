package com.paradigma.filmio.dao.postgres.spring.repository;

import com.paradigma.filmio.core.model.UserSocialStatistics;
import com.paradigma.filmio.dao.postgres.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query("""
    from UserEntity ue
    left join fetch ue.userAccount
    left join fetch ue.country
    left join fetch ue.userEssentialMedia
    left join fetch ue.userEssentialMedia.avatar
    left join fetch ue.userEssentialMedia.backdrop
    where ue.userId = :id
    """)
    Optional<UserEntity> findByIdWithMedias(@Param("id") UUID id);

    @Query("""
    select new com.paradigma.filmio.core.model.UserSocialStatistics(
         (select count(f1) from UserFollowing f1 where f1.followed.userId = :id),
         (select count(f2) from UserFollowing f2 where f2.follower.userId = :id)
    )
    from UserEntity u
    where u.userId = :id
    """)
    UserSocialStatistics findStatisticsById(@Param("id") UUID id);
}
