package com.FXTracker.service;

import com.FXTracker.DTO.UserDto;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.mapper.UserMapper;
import com.FXTracker.model.User;
import com.FXTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling operations on users.
 * Handles operations like creating, getting by Id, updating, finding all of existing users.
 */

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * creates and saves new user to DB
     * @param userDto takes object of class UserDto as a parameter
     * @return User entity
     */
    public User createUser(UserDto userDto) {

        var entity = userMapper.toEntity(userDto);
        userRepository.save(entity);

        return entity;
    }

    /**
     * Handles searching for user by ID
     * @param id represents user ID
     * @return user mapped to UserDto class
     */
    public UserDto getUserById(String id) {

        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new ResourceNotFoundException(String.format("User was not found with given id: %s", id));
        }
        return userMapper.toDto(user.get());
    }
}
