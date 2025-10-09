package com.paradigma.filmio.core.port.out;

import com.paradigma.filmio.core.domain.model.UserAccount;

import java.util.UUID;


public interface TokenDao {
    void createToken(UserAccount userAccount, String token);
    void verifyUserByToken(String token);
    void createPasswordResetToken(UserAccount userAccount, String token);
    UUID findUserIdByPasswordResetToken(String token);
}
