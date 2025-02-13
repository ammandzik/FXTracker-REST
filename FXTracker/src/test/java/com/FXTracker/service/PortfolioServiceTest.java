package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.exception.InsufficientStockException;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.utils.DataTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PortfolioServiceTest {


    private static PortfolioDto p1;
    private HashMap<String, String> stocks = new HashMap<>();

    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private PortfolioMapper portfolioMapper;

    @BeforeEach
    public void initializePortfolioDto() {

        p1 = DataTest.testPortfolio(stocks, 100F, 50F, 0F);
    }


    @BeforeEach
    void addStocksToMap() {

        stocks.put("AAPL", "100");
        stocks.put("TTWO", "20");
        stocks.put("HSBC", "10");
    }

    //todo IT Service - test container required
    @Test
    public void shouldCreatePortfolioCorrectlyIT() {

        //when
        var portfolio = assertDoesNotThrow(() -> portfolioService.createPortfolio(p1), "Should not throw any exceptions.");

        //then
        assertNotNull("Portfolio should not be null", portfolio);
    }

    //todo IT Service - test container required
    @Test
    public void shouldUpdatePortfolioCorrectlyIT() {

        var entityPortfolio = portfolioService.portfolioByUserId("1");


        Assertions.assertNotNull(p1, "Portfolio should not be null.");
        assertEquals("ID's should be equal.", entityPortfolio.getId(), p1.getId());

    }

    @Test
    public void findAllPortfoliosTest() {

        //given
        List<PortfolioDto> portfolioDtos = portfolioService.getAllPortfolios();

        //then
        assertDoesNotThrow(() -> portfolioService.getAllPortfolios(), "Should not throw any exceptions.");
        Assertions.assertNotNull(portfolioDtos, "Portfolios should not be null.");
        assertFalse("Portfolios should not be empty.", portfolioDtos.isEmpty());

    }

    @Test
    public void addStockCorrectlyTest() {

        assertDoesNotThrow(() -> portfolioService.addStock(portfolioMapper.toEnity(p1), 110, "10", "AAPL"), "Should not throw any exceptions.");
        assertEquals(p1.getStocks().get("AAPL"),"110");

    }

    @Test
    public void addStockShouldThrowInsufficientStockExceptionTest() {


        assertThrows("Should throw Insufficient Stock Exception.", InsufficientStockException.class, () -> portfolioService.addStock(portfolioMapper.toEnity(p1), 150, "-151", "AAPL"));


    }


}
