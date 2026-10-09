package com.techcore.auth_service.keycloak.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateUserRequest {

    @NotBlank(message = "Name field mustn't be empty")
    @Size(min = 1, max = 100, message = "Name field must be longer than 1 symbols and shorter than 100 symbols")
    private String name;

    @NotBlank(message = "Surname field mustn't be empty")
    @Size(min = 2, max = 100, message = "Surname field must be longer than 2 symbols shorter than 100 symbols")
    private String surname;

    @Email(message = "The format email is incorrect")
    @NotBlank(message = "Email field mustn't be empty")
    @Size(min = 6, max = 80, message = "The email length must be between 6 and 80")
    private String email;

    @NotBlank(message = "The phone number mustn't be null")
    @Pattern(
            regexp = "\\+375\\d{9}",
            message = "The phone number must consist of format like +375XXXXXXXXX")
    private String phoneNumber;

    @NotNull(message = "Date born can not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private @Past(message = "The Birth date must be in the past") LocalDate birthDate;


    @AssertTrue(message = "User must be older that 18 old year")
    public boolean isAdult(){
        return birthDate != null && birthDate.isBefore(LocalDate.now().minusYears(18));
    }
}
