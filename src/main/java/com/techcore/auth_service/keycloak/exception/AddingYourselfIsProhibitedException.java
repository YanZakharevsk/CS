package com.techcore.auth_service.keycloak.exception;

import org.springframework.http.HttpStatus;

public class AddingYourselfIsProhibitedException extends BaseException {
    public AddingYourselfIsProhibitedException() {
        super("Conflict", "You arent allowed to add yourself to the contact", HttpStatus.CONFLICT);
    }
}
