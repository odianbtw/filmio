package com.paradigma.filmio.api.mapper;


import com.paradigma.filmio.api.model.CreateUserAccountRequestV1;
import com.paradigma.filmio.api.model.UpdateUserV1;
import com.paradigma.filmio.api.model.UserRepresentationV1;
import com.paradigma.filmio.api.model.UserSocialStatisticsV1;
import com.paradigma.filmio.core.domain.model.User;
import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.model.UserSocialStatistics;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = {CountryDTOMapper.class, MediaDTOMapper.class}
)
public interface UserDTOMapper {
    UserAccount toUserAccount (CreateUserAccountRequestV1 createUserAccountRequestV1);
    @Mapping(target = "medias", source = "media")
    UserRepresentationV1 toUserRepresentationV1(User user);
    @Mapping(target = "media", source = "medias")
    @Mapping(target = "country", source = "countryId")
    User toUser(UpdateUserV1 updateUserV1);
    UserSocialStatisticsV1 toUserSocialStatisticsV1(UserSocialStatistics statistics);
}
