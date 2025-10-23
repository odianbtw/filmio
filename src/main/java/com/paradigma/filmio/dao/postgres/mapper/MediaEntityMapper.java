package com.paradigma.filmio.dao.postgres.mapper;

import com.paradigma.filmio.core.domain.model.Media;
import com.paradigma.filmio.dao.postgres.model.MediaEntity;
import com.paradigma.filmio.dao.postgres.model.UserEssentialMedia;
import org.mapstruct.Mapper;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface MediaEntityMapper {
    Media toMedia(MediaEntity media);
    default Set<Media> toMediaSet(UserEssentialMedia userEssentialMedia) {
        final var avatar = toMedia(userEssentialMedia.getAvatar());
        final var backdrop = toMedia(userEssentialMedia.getBackdrop());
        return Stream.of(avatar, backdrop)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
