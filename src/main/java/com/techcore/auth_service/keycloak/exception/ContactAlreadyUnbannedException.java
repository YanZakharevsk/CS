package com.techcore.auth_service.keycloak.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ContactAlreadyUnbannedException extends BaseException {
    public ContactAlreadyUnbannedException(UUID targetId) {
        super("Conflict", "Contact with id " + targetId + "has already unbanned", HttpStatus.CONFLICT);
    }
}
