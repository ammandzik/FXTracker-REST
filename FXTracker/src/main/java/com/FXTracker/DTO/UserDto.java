package com.FXTracker.DTO;

import com.FXTracker.model.Role;
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
    private String name;
    private String surname;
    private String email;
    private String password;
    private List<Role> roles;
    private boolean enabled;
}
