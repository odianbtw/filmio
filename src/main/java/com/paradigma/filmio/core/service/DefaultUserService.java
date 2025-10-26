package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.domain.model.User;
import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.exception.NotFoundException;
import com.paradigma.filmio.core.model.UserSocialStatistics;
import com.paradigma.filmio.core.port.in.MediaService;
import com.paradigma.filmio.core.port.in.UserService;
import com.paradigma.filmio.core.port.out.MediaDao;
import com.paradigma.filmio.core.port.out.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultUserService implements UserService {

    private final UserDao userDao;
    private final MediaService mediaService;
    private final UuidGenerator uuidGenerator;

    @Override
    public void create(UserAccount userAccount) {
        userAccount.setId(uuidGenerator.generateId());
        final var savedUserAccount = userDao.create(userAccount);
    }

    @Override
    public void changePassword(UUID userId, String newPassword) {
        userDao.changeUserPassword(userId, newPassword);
    }

    @Override
    public User findById(UUID id) {
        return userDao.findById(id)
                .orElseThrow(() -> new NotFoundException("User with provided id doesn't exist."));
    }

    @Override
    public void update(User user) {
        final var medias = user.getMedia();
        final var savedMedias = mediaService.saveAll(medias);
        user.setMedia(savedMedias);
        userDao.update(user);
    }

    @Override
    public UserSocialStatistics findStatisticsById(UUID id) {
        return userDao.findStatisticsById(id);
    }


}
