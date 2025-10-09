package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.domain.model.UserAccount;
import com.paradigma.filmio.core.port.in.UserService;
import com.paradigma.filmio.core.port.out.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultUserService implements UserService {

    private final UserDao userRepository;


    @Override
    public void create(UserAccount userAccount) {
        userAccount.setId(UUID.randomUUID());
        userRepository.create(userAccount);
    }

    @Override
    public void changePassword(UUID userId, String newPassword) {
        userRepository.changeUserPassword(userId, newPassword);
    }
}
