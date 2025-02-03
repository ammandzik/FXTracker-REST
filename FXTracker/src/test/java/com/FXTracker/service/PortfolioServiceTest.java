package com.FXTracker.service;

import com.FXTracker.DataTest;
import com.FXTracker.model.Stock;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.TreeMap;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PortfolioServiceTest {

    private TreeMap<Stock, Long> stocks = new TreeMap();

    @Autowired
    private PortfolioService portfolioService;

    @BeforeAll
    void addStocksToMap() {

        stocks.put(DataTest.createStock(), 100L);
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
