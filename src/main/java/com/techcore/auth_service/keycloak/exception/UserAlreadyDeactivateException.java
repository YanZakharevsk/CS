package com.techcore.auth_service.keycloak.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserAlreadyDeactivateException extends BaseException {
    public UserAlreadyDeactivateException(UUID userId) {
        super("Conflict", "User with id " + userId + "has already  deactivated", HttpStatus.CONFLICT);
    }
}
