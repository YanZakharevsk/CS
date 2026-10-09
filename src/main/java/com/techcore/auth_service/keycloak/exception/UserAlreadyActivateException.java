package com.techcore.auth_service.keycloak.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserAlreadyActivateException extends BaseException {
    public UserAlreadyActivateException(UUID userId) {
        super("Conflict", "User with id " + userId + "has already  activated", HttpStatus.CONFLICT);
    }
}
