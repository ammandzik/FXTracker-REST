package com.FXTracker.controller;

import com.FXTracker.DTO.WalletDto;
import com.FXTracker.repository.WalletRepository;
import com.FXTracker.service.WalletService;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("api/wallet")
class WalletController {

    private final WalletRepository walletRepository;
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<?> createWallet(@Valid @RequestBody WalletDto walletDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.warn("Validation failed while creating new wallet");
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(walletService.createWallet(walletDto), HttpStatus.CREATED);
    }
}
