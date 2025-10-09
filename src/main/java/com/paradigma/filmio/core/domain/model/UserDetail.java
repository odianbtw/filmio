package com.paradigma.filmio.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {
    private UserAccount userAccount;
    private Country country;
    private String description;
    private Set<Media> medias;

    public Optional<Media> getAvatar () {
        return medias.stream()
                .filter(m -> Objects.equals(m.getMediaType(), MediaType.AVATAR))
                .findAny();
    }

    public Optional<Media> getBackdrop () {
        return medias.stream()
                .filter(m -> Objects.equals(m.getMediaType(), MediaType.BACKDROP))
                .findAny();
    }
}
