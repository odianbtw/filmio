package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.domain.model.UserRole;
import com.paradigma.filmio.core.model.AccessToken;
import com.paradigma.filmio.core.exception.AccessForbiddenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationUtil implements AuthenticationTokenUtil {

    private final AccessTokenGenerator tokenGenerator;
    private final Key key;

    @Override
    public AccessToken generateTokenFor(UserAccount userAccount) {
        final var accessToken = tokenGenerator.generateAccessToken(userAccount);
        final var refreshToken = tokenGenerator.generateRefreshToken(userAccount);
        return new AccessToken(
                accessToken,
                refreshToken
        );
    }

    @Override
    public AccessToken refreshToken(String refreshToken) {
        if (isValid(refreshToken)) {
            final var user = UserAccount.builder()
                    .username(getUsername(refreshToken))
                    .role(getUserRole(refreshToken))
                    .build();
            final var newAccessToken = tokenGenerator.generateAccessToken(user);
            return new AccessToken(newAccessToken, refreshToken);
        }
        throw new AccessForbiddenException("Refresh token is invalid.");
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    private String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    private UserRole getUserRole(String token) {
        Claims claims = getClaims(token);
        return claims != null ? UserRole.valueOf(claims.get("role", String.class)) : null;
    }

    private boolean isValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(token);
            return true;
        } catch (Exception e) {
            // NOP
        }
        return false;
    }

}
