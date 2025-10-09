package com.paradigma.filmio.core.port.in;

import com.paradigma.filmio.core.model.AccessToken;

public interface AuthenticationService {
    void createResetPasswordToken(String userEmail);
    AccessToken login(String username, String password);
    AccessToken refresh(String refreshToken);
}
