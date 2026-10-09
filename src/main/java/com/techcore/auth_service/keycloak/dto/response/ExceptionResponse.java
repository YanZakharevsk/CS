package com.techcore.auth_service.keycloak.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private String title;
    private String definition;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;
    private String instance;
}
