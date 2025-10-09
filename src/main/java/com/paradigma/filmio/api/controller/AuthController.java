package com.paradigma.filmio.api.controller;

import com.paradigma.filmio.api.mapper.UserDTOMapper;
import com.paradigma.filmio.api.model.*;
import com.paradigma.filmio.core.port.in.UserIdentityService;
import com.paradigma.filmio.core.port.in.UserService;
import com.paradigma.filmio.core.port.in.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthApi {

    private final UserIdentityService userIdentityService;
    private final UserDTOMapper userDTOMapper;
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<Void> registerNewAccount(
            CreateUserAccountRequestV1 createUserAccountRequestV1
    ) {
        log.info("Started creating new user account");
        userIdentityService.createUser(
                userDTOMapper.toUserAccount(createUserAccountRequestV1)
        );
        log.info("Finished creating new user account");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponseEntity<Void> verifyAccount(String token) {
        log.info("Started verifying user account via token");
        userIdentityService.verifyUserAccount(token);
        log.info("Finished verifying user account via token");
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public ResponseEntity<Void> crateResetPasswordToken(
            CreatePasswordResetTokenRequestV1 createPasswordResetTokenRequestV1
    ) {
        log.info("Started creating reset password token.");
        authenticationService.createResetPasswordToken(
                createPasswordResetTokenRequestV1.getEmail()
        );
        log.info("Finished creating reset password token.");
        return ResponseEntity
                .ok()
                .build();
    }

    @Override
    public ResponseEntity<Void> resetPassword(PasswordResetRequestV1 passwordResetRequestV1) {
        log.info("Started resetting password.");
        userIdentityService.changePassword(
                passwordResetRequestV1.getToken(),
                passwordResetRequestV1.getPassword()
        );
        log.info("Finished resetting password.");
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public ResponseEntity<LoginResponseV1> login(LoginRequestV1 loginRequestV1) {
        log.info("Started logging into account");
        final var tokens = authenticationService.login(
                loginRequestV1.getUsername(),
                loginRequestV1.getPassword()
        );
        final var result = new LoginResponseV1(
                tokens.accessToken(),
                tokens.refreshToken()
        );
        log.info("Finished logging into account");
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<GenerateAccessTokenResponseV1> refresh(
            GenerateAccessTokenRequestV1 generateAccessTokenRequestV1
    ) {
        log.info("Started generating new access token via refresh token");
        final var tokens = authenticationService.refresh(
                generateAccessTokenRequestV1.getRefreshToken()
        );
        final var result = new GenerateAccessTokenResponseV1(tokens.accessToken());
        log.info("Finished generating new access token via refresh token");
        return ResponseEntity.ok(result);
    }
}
