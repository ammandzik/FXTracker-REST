package com.FXTracker.DTO.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class UserRegistrationRequestDto {

    @NotBlank
    @Email
    private String email;
}
