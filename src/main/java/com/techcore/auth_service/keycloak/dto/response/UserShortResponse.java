package com.techcore.auth_service.keycloak.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserShortResponse {
    private UUID userId;
    private String name;
    private String surname;
    private Boolean isActive;
}
