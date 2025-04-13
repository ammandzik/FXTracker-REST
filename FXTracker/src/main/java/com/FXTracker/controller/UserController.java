package com.FXTracker.controller;

import com.FXTracker.DTO.UserDto;
import com.FXTracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/user")
@RestController
class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createNewUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.warn("Validation failed while creating new user");
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {

        var user = userService.getUserById(id);

        return ResponseEntity.ok(user);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id) {

        var user = userService.getUserById(id);

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

}
