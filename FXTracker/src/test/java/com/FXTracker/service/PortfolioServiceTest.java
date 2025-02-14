package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.exception.InsufficientStockException;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.utils.DataTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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

    private static HashMap<String, String> stocks = new HashMap<>();
    private static final PortfolioDto PORTFOLIO_DTO = DataTest.testPortfolio(stocks, 0d, 0d, 0d);
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private PortfolioMapper portfolioMapper;

    //todo IT Service - test container required
    @Test
    public void shouldCreatePortfolioCorrectlyIT() {

        //when
        var portfolio = assertDoesNotThrow(() -> portfolioService.createPortfolio(PORTFOLIO_DTO), "Should not throw any exceptions.");

        //then
        assertNotNull("Portfolio should not be null", portfolio);
    }

    //todo IT Service - test container required
    @Test
    public void shouldUpdatePortfolioCorrectlyIT() {

        var entityPortfolio = portfolioService.portfolioByUserId("1");


        Assertions.assertNotNull(PORTFOLIO_DTO, "Portfolio should not be null.");
        assertEquals("ID's should be equal.", entityPortfolio.getId(), PORTFOLIO_DTO.getId());

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

        stocks.put("HSBC", "10");

        assertDoesNotThrow(() -> portfolioService.addStock(portfolioMapper.toEnity(PORTFOLIO_DTO), 20, "10", "HSBC"), "Should not throw any exceptions.");
        assertEquals("Number of stocks should be equal", PORTFOLIO_DTO.getStocks().get("HSBC"), "20");

    }

    @Test
    public void addStockShouldThrowInsufficientStockExceptionTest() {

        stocks.put("AAPL", "100");

        assertThrows("Should throw Insufficient Stock Exception.", InsufficientStockException.class, () -> portfolioService.addStock(portfolioMapper.toEnity(PORTFOLIO_DTO), -1, "-101", "AAPL"));


    }


}
