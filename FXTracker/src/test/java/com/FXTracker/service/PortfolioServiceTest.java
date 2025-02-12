package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.utils.DataTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    public void initializePortfolioDto() {

        p1 = DataTest.testPortfolio(stocks, 100F, 50F, 0F);
    }


    @BeforeAll
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


}
