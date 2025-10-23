package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.domain.model.UserAccount;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenGenerator implements AccessTokenGenerator {

    private final Key secret;

    @Override
    public String generateAccessToken(UserAccount userAccount) {
        return generate(userAccount, 3600);
    }

    @Override
    public String generateRefreshToken(UserAccount userAccount) {
        return generate(userAccount, 1209600);
    }

    private String generate(UserAccount userAccount, long expirationIn) {
        final Instant now = Instant.now();
        final Instant expiration = now.plusMillis(expirationIn);
        return Jwts.builder()
                .setSubject(String.valueOf(userAccount.getId()))
                .claim("role", userAccount.getRole().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(secret,  SignatureAlgorithm.HS256)
                .compact();
    }
}
