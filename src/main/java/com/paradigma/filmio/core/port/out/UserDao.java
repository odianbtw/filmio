package com.paradigma.filmio.core.port.out;

import com.paradigma.filmio.core.domain.model.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    UserAccount create(UserAccount userAccount);
    Optional<UserAccount> findById(UUID id);
    Optional<UserAccount> findByEmail(String email);
    void update(UserAccount userAccount);
    void changeUserPassword(UUID id, String newPassword);
    Optional<UserAccount> findByUsernameAndPassword(String username, String password);
}
