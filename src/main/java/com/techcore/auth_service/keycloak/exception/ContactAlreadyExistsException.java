package com.techcore.auth_service.keycloak.exception;

import org.springframework.http.HttpStatus;

public class ContactAlreadyExistsException extends BaseException {
    public ContactAlreadyExistsException() {
        super("Conflict", "This contact has already created, please check your contacts", HttpStatus.CONFLICT);
    }
}
