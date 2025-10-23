package com.paradigma.filmio.dao.postgres.mapper;


import com.paradigma.filmio.core.domain.model.User;
import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.dao.postgres.model.UserAccountEntity;
import com.paradigma.filmio.dao.postgres.model.UserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = {CountryEntityMapper.class, MediaEntityMapper.class}
)
public interface UserEntityMapper {
    UserAccountEntity toUserAccountEntity(UserAccount userAccount);
    UserAccount toUserAccount(UserAccountEntity userAccountEntity);
    @Mapping(target = "id", source = "userAccount.id")
    @Mapping(target = "username", source = "userAccount.username")
    @Mapping(target = "media", source = "userEssentialMedia")
    User toUser(UserEntity entity);
}
