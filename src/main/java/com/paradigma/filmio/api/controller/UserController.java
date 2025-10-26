package com.paradigma.filmio.api.controller;

import com.paradigma.filmio.api.exception.BadRequestException;
import com.paradigma.filmio.api.mapper.UserDTOMapper;
import com.paradigma.filmio.api.model.*;
import com.paradigma.filmio.api.validator.PageableValidator;
import com.paradigma.filmio.core.port.in.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UsersApi {

    private final UserService userService;
    private final UserDTOMapper userMapper;
    private final PageableValidator pageableValidator;

    @Override
    public ResponseEntity<UserRepresentationV1> findUserById(UUID id) {
        log.info("Started finding user by id - {}", id);
        final var user = userService.findById(id);
        log.info("Finished finding user by id - {}", id);
        return ResponseEntity.ok(
                userMapper.toUserRepresentationV1(user)
        );
    }

    @Override
    public ResponseEntity<Void> updateUser(UUID id, UpdateUserV1 updateUserV1) {
        log.info("Started updating user by id - {}", id);
        if (!Objects.equals(id, updateUserV1.getId()))
            throw new BadRequestException("User id in path and in request body must be equal.");
        userService.update(
               userMapper.toUser(updateUserV1)
        );
        log.info("Finished updating user by id - {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public ResponseEntity<UserSocialStatisticsV1> findUserStatisticsById(UUID id) {
        log.info("Started finding user social statistic by id - {}", id);
        final var statistics = userService.findStatisticsById(id);
        log.info("Finished finding user social statistic by id - {}", id);
        return ResponseEntity.ok(
                userMapper.toUserSocialStatisticsV1(statistics)
        );
    }

    @Override
    public ResponseEntity<GetUsersV1> getUserFollowersById(PageModel page) {
        pageableValidator.validate(page);
        userService.
    }
}
