package com.FXTracker.controller;

import com.FXTracker.DTO.UserDto;
import com.FXTracker.model.User;
import com.FXTracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/user")
@RestController
class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createNewUser(@RequestBody UserDto userDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {

        var user = userService.getUserById(id);

        return ResponseEntity.ok(user);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id) {

        var user = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

}
