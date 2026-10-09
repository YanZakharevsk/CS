package com.techcore.auth_service.keycloak.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactRequest {

    @NotNull(message = "Your id can not be null")
    UUID ownerId;
    @NotNull(message = "The contact id can not be null")
    UUID targetId;
}
