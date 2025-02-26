package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.exception.InsufficientStockException;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.mapper.StockMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.repository.StockRepository;
import com.FXTracker.utils.DataTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PortfolioServiceTest {
    private static HashMap<String, String> stocks;
    private static Portfolio portfolio;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private PortfolioMapper portfolioMapper;
    @Autowired
    private StockService stockService;

    @BeforeAll
    static void setUp() {

        stocks = new HashMap<>();
        stocks.put("AAPL", "10");
        stocks.put("HSBC", "10");
        stocks.put("TSLA", "10");

        portfolio = DataTest.createPortfolioDto(stocks, "1", 0d, 0d, 0d, 3000d);
    }

    @BeforeEach
    public void setUpMongoDB() {

        mongoTemplate.save((portfolio), "portfolios");
    }

    @Test
    void shouldCreatePortfolioCorrectlyIT() {

        //when
        assertDoesNotThrow(() -> portfolioService.createPortfolio(portfolioMapper.toDto(portfolio)), "Creating portfolio should not throw any exceptions.");

        //then
        assertNotNull(portfolio, "Created portfolio should not be null");
    }

    @Test
    void shouldUpdatePortfolioCorrectlyIT() {


        portfolioService.updatePortfolio(portfolio, "HSBC", "20");

        assertNotNull(portfolio, "Portfolio should not be null.");
        assertEquals("30", portfolio.getStocks().get("HSBC"), "Number of stocks should be equal to 30");

    }

    @Test
    void findAllPortfoliosTest() {

        //when
        List<PortfolioDto> portfolioDtos = assertDoesNotThrow(() -> portfolioService.getAllPortfolios(), "Should not throw any exceptions.");

        //then
        assertNotNull(portfolioDtos, "Portfolios should not be null.");
        assertTrue(!portfolioDtos.isEmpty(), "Portfolios size should be more than 0.");

    }

    @Test
    void addStockCorrectlyTest() {

        //given
        var map = portfolio.getStocks();

        //when
        assertDoesNotThrow(() -> portfolioService.addStock(map, "20", "HSBC"), "Should not throw any exceptions.");

        //then
        assertEquals("30", map.get("HSBC"), "Number of stocks should be equal.");

    }

    @Test
    void addStockShouldThrowInsufficientStockExceptionTest() {

        var portfolioStocks = portfolio.getStocks();

        assertThrows(InsufficientStockException.class, () -> portfolioService.addStock(portfolioStocks, "-11", "AAPL"));
    }

    @Test
    void parseIfContainsSymbol() {

        assertEquals(10, portfolioService.parseIfContainsSymbol(portfolio.getStocks(), "AAPL"));
    }

    @Test
    void parseIfContainsSymbolShouldReturn0IfSymbolDoesNotExist() {

        assertEquals(0, portfolioService.parseIfContainsSymbol(portfolio.getStocks(), "TTWO"));
    }

    @Test
    void parseIfContainsSymbolShouldThrowResourceNotFoundExceptionIfSymbolIsNull() {

        var portfolioStocks = portfolio.getStocks();

        assertThrows(ResourceNotFoundException.class, () -> portfolioService.parseIfContainsSymbol(portfolioStocks, null));
    }

    @Test
    void countBalance() {

        // to be written
    }

    @Test
    void trackFundsSpentOnStocks() {

        //to be written
    }
}
