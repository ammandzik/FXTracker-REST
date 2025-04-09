package com.FXTracker.service;

import com.FXTracker.DTO.WalletDto;
import com.FXTracker.exception.WalletServiceException;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.model.Stock;
import com.FXTracker.model.User;
import com.FXTracker.model.Wallet;
import com.FXTracker.utils.DataTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class WalletServiceTest {
    private static WalletDto w1;
    private static HashMap<String, String> stocks;
    private static Portfolio portfolio;
    private static User user;
    private static Wallet wallet;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private PortfolioMapper portfolioMapper;
    @Autowired
    private StockService stockService;
    @Autowired
    private WalletService walletService;

    @BeforeAll
    static void setUp() {

        stocks = new HashMap<>();
        stocks.put("AAPL", "10");
        stocks.put("HSBC", "10");
        stocks.put("TSLA", "10");
        portfolio = DataTest.createPortfolioDto(stocks, "1", 0d, 0d, 0d, 0d);
        user = DataTest.createUser();
        wallet = DataTest.createWallet();

    }

    @BeforeEach
    public void setUpMongoDB() {

        mongoTemplate.save(new Stock("1", "HSBC", "56.08", "56.08", "0,20", null), "stocks");
        mongoTemplate.save(new Stock("2", "TTWO", "211.65", "211.65", "-1.67", null), "stocks");
        mongoTemplate.save(new Stock("3", "TSLA", "337.80", "337.80", "-4.68", null), "stocks");
        mongoTemplate.save(new Stock("4", "AAPL", "245.55", "245.55", "-0.28", null), "stocks");
        mongoTemplate.save((portfolio), "portfolios");
        mongoTemplate.save((user), "users");
        mongoTemplate.save(wallet, "wallets");
    }

    @Test
    void shouldCreateWalletCorrectlyIT() {

        var savedWallet = assertDoesNotThrow(() -> walletService.createWallet(w1), "Creating wallet should not throw any exceptions.");

        //then
        assertNotNull(savedWallet, "Wallet should not be null");
    }

    @Test
    void creatingWalletWithNullValueShouldThrowException() {

        assertThrows(WalletServiceException.class, (() -> walletService.createWallet(null)), "Should throw WalletServiceException");
    }

    @Test
    void addFundsToWalletCorrectly() {

        var wallet = assertDoesNotThrow(() -> walletService.updateWalletBalance("1", -2000d));
        assertEquals(302000, wallet.getBalance());

    }

    @Test
    void decreaseFundsInWalletAfterBuyingStock() {
        var wallet = assertDoesNotThrow(() -> walletService.updateWalletBalance("1", 2000d));
        assertEquals(298000, wallet.getBalance());

    }

}
