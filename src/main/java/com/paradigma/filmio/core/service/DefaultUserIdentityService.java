package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.port.in.UserIdentityService;
import com.paradigma.filmio.core.port.in.UserService;
import com.paradigma.filmio.core.port.out.TokenDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultUserIdentityService implements UserIdentityService {


    private final UserService userService;
    private final TokenDao tokenDao;
    private final VerificationTokenGenerator tokenGenerator;
    private final MessageGenerator messageGenerator;
    private final EmailService emailService;


    @Override
    public void createUser(UserAccount userAccount) {
        userService.create(userAccount);
        final var emailVerificationToken = tokenGenerator.generate();
        tokenDao.createToken(userAccount, emailVerificationToken);
        final var message = messageGenerator.generateEmailVerificationTokenMessage(
                userAccount.getEmail(),
                emailVerificationToken
        );
        emailService.sendEmail(message);
    }

    @Override
    public void changePassword(String resetToken, String newPassword) {
        final var userId = tokenDao.findUserIdByPasswordResetToken(
                resetToken
        );
        userService.changePassword(userId, newPassword);
    }


    @Override
    public void verifyUserAccount(String token) {
        tokenDao.verifyUserByToken(token);
    }
}
