package com.paradigma.filmio.core.model;

public record UserSocialStatistics(
        Integer followers,
        Integer following
) implements SocialStatistics{
}
