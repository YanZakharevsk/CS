package com.techcore.auth_service.keycloak.exception;

import org.springframework.http.HttpStatus;

public class EmailHasAlreadyUsedException extends BaseException {
    public EmailHasAlreadyUsedException(String email) {
        super("Conflict", "User with email " + email + " has already existed", HttpStatus.CONFLICT);
    }
}
