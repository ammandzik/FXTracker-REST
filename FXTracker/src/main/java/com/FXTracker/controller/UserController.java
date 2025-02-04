package com.FXTracker.controller;

import com.FXTracker.model.User;
import com.FXTracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/user")
@RestController
class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createNewUser(@RequestBody User user) {

        return ResponseEntity.ok(userService.createUser(user));

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){

        var user = userService.getUserById(id);

        return ResponseEntity.ok(user);

    }
}
