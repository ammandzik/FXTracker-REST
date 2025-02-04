package com.FXTracker.service;

import com.FXTracker.DataTest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PortfolioServiceTest {

    private HashMap<String, Long> stocks = new HashMap<>();

    @Autowired
    private PortfolioService portfolioService;

    @BeforeAll
    void addStocksToMap() {

        stocks.put("AAPL", 100L);
        stocks.put("TTWO", 20L);
        stocks.put("HSBC", 11L);
    }

    @Test
    public void shouldCreatePortfolioCorrectly() {

        //when
        var portfolio = assertDoesNotThrow(() -> portfolioService.createPortfolio(DataTest.testPortfolio(stocks)));

        //then
        assertNotNull("Portfolio should not be null", portfolio);
    }

    @Test
    public void shouldUpdatePortfolioCorrectly() {


    }


}
