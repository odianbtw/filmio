package com.paradigma.filmio.api.mapper;


import com.paradigma.filmio.api.model.UserEssentialMediaV1;
import com.paradigma.filmio.core.domain.model.Media;
import com.paradigma.filmio.core.domain.model.MediaType;
import org.mapstruct.Mapper;

import java.net.URI;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface MediaDTOMapper {
    default UserEssentialMediaV1 toUserEssentialMediaV1(Set<Media> medias) {
        final var avatar = medias
                .stream()
                .filter(m -> Objects.equals(m.getMediaType(), MediaType.AVATAR))
                .findFirst()
                .orElse(null);
        final var backdrop = medias
                .stream()
                .filter(m -> Objects.equals(m.getMediaType(), MediaType.BACKDROP))
                .findFirst()
                .orElse(null);
        final var avatarUrl = (avatar == null) ? null : URI.create(avatar.getUrl());
        final var backdropUrl = (backdrop == null) ? null : URI.create(backdrop.getUrl());
        return new UserEssentialMediaV1(avatarUrl, backdropUrl);
    }

    default Set<Media> toMediaSet(UserEssentialMediaV1 medias) {
        if (medias == null) return null;
        final var avatar = new Media(
                null,
                (medias.getAvatarUrl() == null) ? null : medias.getAvatarUrl().toString(),
                MediaType.AVATAR
        );
        final var backdrop = new Media(
                null,
                (medias.getAvatarUrl() == null) ? null : medias.getAvatarUrl().toString(),
                MediaType.BACKDROP
        );
        return Stream.of(avatar, backdrop)
                .filter(obj -> Objects.nonNull(obj.getUrl()))
                .collect(Collectors.toSet());
    }

}
