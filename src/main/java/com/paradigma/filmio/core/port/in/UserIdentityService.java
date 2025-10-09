package com.paradigma.filmio.core.port.in;

import com.paradigma.filmio.core.domain.model.UserAccount;

public interface UserIdentityService {
    void createUser(UserAccount userAccount);
    void changePassword(String resetToken, String newPassword);
    void verifyUserAccount(String token);
}
