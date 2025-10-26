package com.paradigma.filmio.core.port.out;

import com.paradigma.filmio.core.domain.model.User;
import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.model.UserSocialStatistics;

import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    UserAccount create(UserAccount userAccount);
    void update(User user);
    UserSocialStatistics findStatisticsById(UUID id);
    Optional<User> findById(UUID id);
    Optional<UserAccount> findByEmail(String email);
    void changeUserPassword(UUID id, String newPassword);
    Optional<UserAccount> findByUsernameAndPassword(String username, String password);
}
