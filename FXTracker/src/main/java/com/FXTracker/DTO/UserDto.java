package com.FXTracker.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents User DTO with details such as id, name, surname, email, password and roles.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;
    @NotEmpty(message = "Name must be filled in!")
    private String name;
    @NotEmpty(message = "Surname must be filled in!")
    private String surname;
    @NotEmpty(message = "Email cannot be empty!")
    private String email;
    @NotBlank
    private String password;
    private List<String> roles;
}
