package com.techcore.auth_service.keycloak.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private String title;
    private String definition;
    private HttpStatus httpStatus;

    public BaseException(String title, String definition, HttpStatus status) {
        super(definition);
        this.title = title;
        this.definition = definition;
        this.httpStatus = status;
    }
}
