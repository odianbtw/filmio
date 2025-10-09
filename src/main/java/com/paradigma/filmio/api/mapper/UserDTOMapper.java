package com.paradigma.filmio.api.mapper;


import com.paradigma.filmio.api.model.CreateUserAccountRequestV1;
import com.paradigma.filmio.core.domain.model.UserAccount;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDTOMapper {
    UserAccount toUserAccount (CreateUserAccountRequestV1 createUserAccountRequestV1);
}
