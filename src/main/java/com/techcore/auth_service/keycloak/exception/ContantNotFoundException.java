package com.techcore.auth_service.keycloak.exception;

import com.techcore.auth_service.keycloak.entity.ContactId;
import org.springframework.http.HttpStatus;

public class ContantNotFoundException extends BaseException {
    public ContantNotFoundException(ContactId contactId) {
        super("NOT_FOUND", "Contact with id " + contactId + "not found", HttpStatus.NOT_FOUND);
    }
}
