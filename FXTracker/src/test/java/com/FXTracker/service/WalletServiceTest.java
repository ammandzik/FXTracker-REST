package com.FXTracker.service;

import com.FXTracker.DTO.WalletDto;
import com.FXTracker.exception.WalletServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class WalletServiceTest {

    private static WalletDto w1;
    @Autowired
    private WalletService walletService;

    @Test
    void shouldCreateWalletCorrectlyIT() {


        var wallet = assertDoesNotThrow(() -> walletService.createWallet(w1), "Creating wallet should not throw any exceptions.");

        //then
        assertNotNull(wallet, "Wallet should not be null");
    }

    @Test
    void creatingWalletWithNullValueShouldThrowException() {

        assertThrows(WalletServiceException.class, (() -> walletService.createWallet(null)), "Should throw WalletServiceException");

    }

    @Test
    void addFundsToWalletCorrectly() {

    }

    @Test
    void decreaseFundsInWalletAfterBuyingStock() {

    }

    @Test
    void shouldThrowInsufficientFundsExceptionWhileBuyingStock() {

    }
}
