package com.FXTracker.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Document(collection = "tokens")
public class VerificationToken {

    private String id;
    @Field(name = "token")
    private String token;
    @Field(name = "email")
    private String email;
    @Field(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Field(name = "expiresAt")
    private LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);
    @Field(name = "confirmed")
    private boolean confirmed = false;

    public VerificationToken(String token, String email) {
        this.token = token;
        this.email = email;
    }
}
