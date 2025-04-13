package com.FXTracker.service;

import com.FXTracker.DTO.UserDto;
import com.FXTracker.DTO.registration.UserCompleteRegistrationDto;
import com.FXTracker.exception.ExistingResourceException;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.exception.TokenExpiredException;
import com.FXTracker.exception.UserServiceException;
import com.FXTracker.mapper.UserMapper;
import com.FXTracker.model.Role;
import com.FXTracker.model.User;
import com.FXTracker.model.VerificationToken;
import com.FXTracker.repository.TokenRepository;
import com.FXTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for handling operations on users.
 * Handles operations like creating, getting by ID, updating, finding all of existing users.
 */

@RequiredArgsConstructor
@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;

    /**
     * creates and saves new user to DB
     *
     * @param userDto takes object of class UserDto as a parameter
     * @return User entity
     */
    public User createUser(UserDto userDto) {

        log.info("Invoked createUser method");

        var entity = userMapper.toEntity(userDto);
        userRepository.save(entity);

        log.info("Saving created user with ID {} to DB", entity.getId());
        return entity;
    }

    /**
     * Handles searching for user by ID
     *
     * @param id represents user ID
     * @return user mapped to UserDto class
     */
    public UserDto getUserById(String id) {

        log.info("Invoked getUserById method");

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.warn("User with ID {} does not exist", id);
            throw new ResourceNotFoundException(String.format("User was not found with given id: %s", id));
        }

        log.info("Retrieving user by ID {} from DB", id);
        return userMapper.toDto(user.get());
    }

    public void sendActivationLink(String email) {

        var token = UUID.randomUUID().toString();
        tokenRepository.save(new VerificationToken(token, email));

        var link = "http://localhost:8080/api/register/complete?token=" + token;
        emailService.send(email, "Complete your registration here: " + link);
    }

    @Transactional
    public void completeRegistration(UserCompleteRegistrationDto userDto) {

        if (userDto == null) {
            log.warn("UserCompleteRegistrationDto data is null!");
            throw new UserServiceException("Cannot finish registration. User data is empty.");
        }

        var token = tokenRepository.findByToken(userDto.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("Provided token was not found."));

        if(token.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new TokenExpiredException("Token has already expired.");
        }

        if (token.isConfirmed()) {
            throw new TokenExpiredException("Token has been already used.");
        }

        if (userExists(token.getEmail())) {
            throw new ExistingResourceException(String.format("User with given email address %s already exists!", token.getEmail()));
        }

        List<Role> roles = new ArrayList<>();
        roles.add(Role.CLIENT);
        token.setConfirmed(true);

        var user = User.builder()
                .email(token.getEmail())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .password(userDto.getPassword())
                .roles(roles)
                .enabled(true)
                .build();

        userRepository.save(user);
        tokenRepository.delete(token);
    }

    private boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
