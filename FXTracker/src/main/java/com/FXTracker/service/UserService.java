package com.FXTracker.service;

import com.FXTracker.DTO.UserDto;
import com.FXTracker.mapper.UserMapper;
import com.FXTracker.model.User;
import com.FXTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser(UserDto userDto) {

        var entity = userMapper.toEntity(userDto);
        userRepository.save(entity);

        return entity;
    }

    public User getUserById(Long id) {

        return null;
    }
}
