package com.paradigma.filmio.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String description;
    private Country country;
    private Set<Media> media;

    public Optional<Media> getAvatar () {
        return media.stream()
                .filter(m -> Objects.equals(m.getMediaType(), MediaType.AVATAR))
                .findAny();
    }

    public Optional<Media> getBackdrop () {
        return media.stream()
                .filter(m -> Objects.equals(m.getMediaType(), MediaType.BACKDROP))
                .findAny();
    }
}
