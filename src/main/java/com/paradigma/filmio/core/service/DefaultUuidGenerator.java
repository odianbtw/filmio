package com.paradigma.filmio.core.service;

import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class DefaultUuidGenerator implements UuidGenerator{
    @Override
    public UUID generateId() {
        return UUID.randomUUID();
    }
}
