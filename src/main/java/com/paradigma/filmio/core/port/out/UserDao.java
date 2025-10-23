package com.paradigma.filmio.core.port.out;

import com.paradigma.filmio.core.domain.model.User;
import com.paradigma.filmio.core.domain.model.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    UserAccount create(UserAccount userAccount);
    User update(User user);
//    Optional<UserAccount> findById(UUID id);
    Optional<User> findById(UUID id);
    Optional<UserAccount> findByEmail(String email);
    void changeUserPassword(UUID id, String newPassword);
    Optional<UserAccount> findByUsernameAndPassword(String username, String password);
}
