package com.paradigma.filmio.api.controller;


import com.paradigma.filmio.api.model.DefaultErrorResponseV1;
import com.paradigma.filmio.core.exception.AccessForbiddenException;
import com.paradigma.filmio.core.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.OffsetDateTime;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<DefaultErrorResponseV1> notFoundException(
            NotFoundException ex
    ) {
        log.error(ex.getMessage(), ex);
        final var response = new DefaultErrorResponseV1();
        response.setMessage(ex.getMessage());
        response.setStatusCode(404);
        response.setTimestamp(OffsetDateTime.from(Instant.now()));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(AccessForbiddenException.class)
    private ResponseEntity<DefaultErrorResponseV1> accessForbiddenException(
            AccessForbiddenException ex
    ) {
        log.error(ex.getMessage(), ex);
        final var response = new DefaultErrorResponseV1();
        response.setMessage(ex.getMessage());
        response.setStatusCode(403);
        response.setTimestamp(OffsetDateTime.from(Instant.now()));
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }
}
