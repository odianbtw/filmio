package com.paradigma.filmio.core.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class DefaultVerificationTokenGenerator implements VerificationTokenGenerator {

    private static final SecureRandom random = new SecureRandom();

    @Override
    public String generate() {
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }
}
