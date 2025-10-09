package com.paradigma.filmio.dao.postgres.mapper;


import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.dao.postgres.model.UserAccountEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {
    UserAccountEntity toUserAccountEntity(UserAccount userAccount);
    UserAccount toUserAccount(UserAccountEntity userAccountEntity);
}
