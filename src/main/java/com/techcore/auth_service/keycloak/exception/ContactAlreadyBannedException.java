package com.techcore.auth_service.keycloak.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ContactAlreadyBannedException extends BaseException {
    public ContactAlreadyBannedException(UUID targetId) {
        super("CONFLICT", "Contact with id " + targetId + " has already banned", HttpStatus.CONFLICT);
    }
}
