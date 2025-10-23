package com.paradigma.filmio.api.controller;

import com.paradigma.filmio.api.exception.BadRequestException;
import com.paradigma.filmio.api.mapper.UserDTOMapper;
import com.paradigma.filmio.api.model.UpdateUserV1;
import com.paradigma.filmio.api.model.UserRepresentationV1;
import com.paradigma.filmio.core.port.in.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        if (!Objects.equals(id, updateUserV1.getId()))
            throw new BadRequestException("User id in path and in request body must be equal.");

    }
}
