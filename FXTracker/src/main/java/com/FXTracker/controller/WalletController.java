package com.FXTracker.controller;

import com.FXTracker.DTO.WalletDto;
import com.FXTracker.model.Wallet;
import com.FXTracker.repository.WalletRepository;
import com.FXTracker.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/wallet")
class WalletController {

    private final WalletRepository walletRepository;
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@Valid @RequestBody WalletDto walletDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.createWallet(walletDto));
    }
}
