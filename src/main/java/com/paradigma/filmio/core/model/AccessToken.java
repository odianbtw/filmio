package com.paradigma.filmio.core.model;

public record AccessToken(
        String accessToken,
        String refreshToken
) {
}
