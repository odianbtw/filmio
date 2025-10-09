package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.model.AccessToken;

public interface AuthenticationTokenUtil {
    AccessToken generateTokenFor(UserAccount userAccount);
    AccessToken refreshToken(String refreshToken);
}
