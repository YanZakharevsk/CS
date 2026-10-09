package com.techcore.auth_service.keycloak.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(UUID userId) {
        super("UserNotFound", "User with id " + userId + " not found", HttpStatus.NOT_FOUND);
    }
}
