package com.techcore.auth_service.keycloak.exception;

import org.springframework.http.HttpStatus;

public class PhoneNumberHasAlreadyBookedException extends BaseException {
    public PhoneNumberHasAlreadyBookedException(String phoneNumber) {
        super("CONFLICT", " User with phone number like " + phoneNumber + " has already existed. Please, choose another phoneNumber", HttpStatus.CONFLICT);
    }
}
