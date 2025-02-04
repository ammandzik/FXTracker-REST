package com.FXTracker.service;

import com.FXTracker.model.User;
import com.FXTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    public User createUser(User user){

        userRepository.save(user);
        return user;
    }

    public User getUserById(Long id){

        return userRepository.findById(id).get();
    }
}
