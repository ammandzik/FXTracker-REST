package com.FXTracker.service;

import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.exception.TokenExpiredException;
import com.FXTracker.model.VerificationToken;
import com.FXTracker.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public static void validateToken(VerificationToken token) {
        if (tokenExpired(token)) {
            throw new TokenExpiredException("Token has already expired.");
        }

        if (tokenUsed(token)) {
            throw new TokenExpiredException("Token has been already used.");
        }
    }

    private static boolean tokenUsed(VerificationToken token) {

        return token.isConfirmed();
    }

    private static boolean tokenExpired(VerificationToken token) {

        return token.getExpiresAt().isBefore(LocalDateTime.now());
    }

    public VerificationToken findByToken(String token) {

        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid or missing token"));
    }
}
