package com.FXTracker.service;

import com.FXTracker.model.VerificationToken;
import com.FXTracker.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public static boolean tokenUsed(VerificationToken token) {

        return token.isConfirmed();
    }

    public static boolean tokenExpired(VerificationToken token) {

        return token.getExpiresAt().isBefore(LocalDateTime.now());
    }
}
