package com.FXTracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "surname")
    private String surname;
    @Field(name = "email")
    private String email;
    @Field(name = "password")
    private String password;
    @Field(name = "roles")
    private List<String> roles;
}