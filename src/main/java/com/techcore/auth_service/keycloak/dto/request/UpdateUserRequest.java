package com.techcore.auth_service.keycloak.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "Name field can not be empty")
    @Size(min = 1, max = 100, message = "The length of the name must be between 1 and 100 symbols")
    private String name;
    @NotBlank(message = "Surname field can not be empty")
    @Size(min = 2, max = 100, message = "The length of the surname must be between 2 and 100 symbols")
    private String surname;

    @NotBlank(message = "The phone number mustn't be null")
    @Pattern(
            regexp = "\\+375\\d{9}",
            message = "The phone number must be in a format like +375XXXXXXXXX")
    private String phoneNumber;
}
