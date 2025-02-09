package com.FXTracker.service;

import com.FXTracker.DTO.WalletDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WalletServiceTest {

    private static WalletDto w1;

    @Autowired
    private WalletService walletService;


    @Test
    public void shouldCreateWalletCorrectlyIT() {


        var wallet = assertDoesNotThrow(() -> walletService.createWallet(w1), "Creating wallet should not throw any exceptions.");

        //then
        assertNotNull("Wallet should not be null", wallet);
    }
}
