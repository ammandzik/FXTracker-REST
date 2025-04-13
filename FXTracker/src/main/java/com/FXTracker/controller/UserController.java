package com.FXTracker.controller;

import com.FXTracker.DTO.registration.UserCompleteRegistrationDto;
import com.FXTracker.DTO.registration.UserRegistrationRequestDto;
import com.FXTracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/user")
@RestController
class UserController {

    private final UserService userService;

    @PostMapping("/activation")
    public ResponseEntity<?> registerEmail(@RequestBody @Valid UserRegistrationRequestDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.warn("Validation failed while sending activation link");
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        userService.sendActivationLink(userDto.getEmail());
        return new ResponseEntity<>("Email with confirmation link has been sent.", HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<?> completeRegistration(@RequestBody @Valid UserCompleteRegistrationDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.warn("Validation failed while creating new user");
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        userService.completeRegistration(userDto);
        return ResponseEntity.ok("User has been registered successfully");
    }

}
