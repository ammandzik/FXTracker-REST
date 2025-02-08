package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.DataTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PortfolioServiceTest {

    private HashMap<String, String> stocks = new HashMap<>();
    private PortfolioDto portfolioDto;

    @Autowired
    private PortfolioService portfolioService;

    @BeforeAll
    void addStocksToMap() {

        stocks.put("AAPL", "100");
        stocks.put("TTWO", "20");
        stocks.put("HSBC", "10");
    }

    //todo IT Service - test container required
    @Test
    public void shouldCreatePortfolioCorrectly() {

        //when
        var portfolio = assertDoesNotThrow(() -> portfolioService.createPortfolio(DataTest.testPortfolio(stocks)));

        //then
        assertNotNull("Portfolio should not be null", portfolio);
    }

    //todo IT Service - test container required
    @Test
    public void shouldUpdatePortfolioCorrectly() {

        var entityPortfolio = portfolioService.portfolioByUserId("1");

        //when
        portfolioDto = portfolioService.updatePortfolio("1", DataTest.testPortfolio(stocks));

        //then
        Assertions.assertNotNull(portfolioDto, "Portfolio should not be null.");
        assertEquals(entityPortfolio.getId(), portfolioDto.getId());

    }


}
