package com.FXTracker.service;

import com.FXTracker.DTO.UserDto;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.mapper.UserMapper;
import com.FXTracker.model.User;
import com.FXTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling operations on users.
 * Handles operations like creating, getting by Id, updating, finding all of existing users.
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * creates and saves new user to DB
     * @param userDto takes object of class UserDto as a parameter
     * @return User entity
     */
    public User createUser(UserDto userDto) {

        logger.info("Invoked createUser method");

        var entity = userMapper.toEntity(userDto);
        userRepository.save(entity);

        logger.info("Saving created user {} to DB", userDto);
        return entity;
    }

    /**
     * Handles searching for user by ID
     * @param id represents user ID
     * @return user mapped to UserDto class
     */
    public UserDto getUserById(String id) {

        logger.info("Invoked getUserById method");

        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            logger.warn("User with ID {} does not exist", id);
            throw new ResourceNotFoundException(String.format("User was not found with given id: %s", id));
        }

        logger.info("Retrieving user by ID {} from DB", id);
        return userMapper.toDto(user.get());
    }
}
