package com.paradigma.filmio.core.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class DefaultPasswordResetTokenGenerator implements PasswordResetTokenGenerator {

    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TOKEN_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    @Override
    public String generate() {
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int index = random.nextInt(CHARSET.length());
            token.append(CHARSET.charAt(index));
        }
        return token.toString();
    }
}
