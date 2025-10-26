package com.paradigma.filmio.core.port.in;

import com.paradigma.filmio.core.domain.model.User;
import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.model.UserSocialStatistics;

import java.util.UUID;

public interface UserService {
    void create(UserAccount userAccount);
    void changePassword(UUID userId, String newPassword);
    User findById(UUID id);
    void update(User user);
    UserSocialStatistics findStatisticsById(UUID id);
}
