package com.FXTracker.DTO.registration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCompleteRegistrationDto {

    @NotBlank
    private String token;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}
