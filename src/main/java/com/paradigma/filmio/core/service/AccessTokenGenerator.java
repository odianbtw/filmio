package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.domain.model.UserAccount;

public interface AccessTokenGenerator extends TokenGenerator {
    String generateAccessToken(UserAccount account);
    String generateRefreshToken(UserAccount account);
}
