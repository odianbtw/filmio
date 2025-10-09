package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.model.AccessToken;
import com.paradigma.filmio.core.exception.NotFoundException;
import com.paradigma.filmio.core.port.in.AuthenticationService;
import com.paradigma.filmio.core.port.out.TokenDao;
import com.paradigma.filmio.core.port.out.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {

    private final TokenDao tokenDao;
    private final UserDao userRepository;
    private final PasswordResetTokenGenerator tokenGenerator;
    private final AuthenticationTokenUtil authenticationTokenUtil;
    private final MessageGenerator messageGenerator;
    private final EmailService emailService;


    @Transactional
    @Override
    public void createResetPasswordToken(String userEmail) {
        final var user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) return;
        final var token = tokenGenerator.generate();
        tokenDao.createPasswordResetToken(user.get(), token);
        final var message = messageGenerator.generatePasswordResetTokenMessage(
                user.get().getEmail(),
                token
        );
        emailService.sendEmail(message);
    }

    @Override
    public AccessToken login(String username, String password) {
        final var user = userRepository.findByUsernameAndPassword(
                username,
                password
        ).orElseThrow(() -> new NotFoundException("Wrong password or username"));
        return authenticationTokenUtil.generateTokenFor(user);
    }

    @Override
    public AccessToken refresh(String refreshToken) {
        return authenticationTokenUtil.refreshToken(refreshToken);
    }


}
