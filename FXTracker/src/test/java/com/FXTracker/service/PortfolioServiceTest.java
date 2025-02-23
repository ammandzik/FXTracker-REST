package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.exception.InsufficientStockException;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.model.Stock;
import com.FXTracker.utils.DataTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class PortfolioServiceTest {
    private static HashMap<String, String> stocks;
    private static PortfolioDto portfolioDto;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private PortfolioMapper portfolioMapper;

    @BeforeAll
    static void setUp() {

        stocks = new HashMap<>();
        stocks.put("AAPL", "10");
        stocks.put("HSBC", "10");
        stocks.put("TSLA", "10");

        portfolioDto = DataTest.createPortfolioDto(stocks, "1", 0d, 0d, 0d, 3000d);
    }

    @BeforeEach
    public void setUpMongoDB() {

        mongoTemplate.save(new Stock("1", "HSBC", "56.08", "56.08", "0,20", null), "stocks");
        mongoTemplate.save(new Stock("2", "TTWO", "211.65", "211.65", "-1.67", null), "stocks");
        mongoTemplate.save(new Stock("3", "TSLA", "337.80", "337.80", "-4.68", null), "stocks");
        mongoTemplate.save(new Stock("4", "AAPL", "245.55", "245.55", "-0.28", null), "stocks");

        mongoTemplate.save(portfolioDto, "portfolios");
        mongoTemplate.save(DataTest.createPortfolioDto(stocks, "2", 20444d, 15023d, 0d, 1000d), "portfolios");
        mongoTemplate.save(DataTest.createPortfolioDto(stocks, "3", 750d, 250d, 0d, 2000d), "portfolios");
    }

    @AfterEach
    public void cleanUpMongoDB() {

        mongoTemplate.dropCollection(Portfolio.class);
        mongoTemplate.dropCollection(Stock.class);
    }

    @Test
    void shouldCreatePortfolioCorrectlyIT() {

        //when
        var portfolio = assertDoesNotThrow(() -> portfolioService.createPortfolio(portfolioDto), "Creating portfolio should not throw any exceptions.");

        //then
        assertNotNull(portfolio, "Created portfolio should not be null");
    }

    @Test
    void shouldUpdatePortfolioCorrectlyIT() {

        //given
        var portfolio = portfolioMapper.toEnity(portfolioDto);

        portfolioService.updatePortfolio(portfolio, "HSBC", "20");

        assertNotNull(portfolioDto, "Portfolio should not be null.");
        assertEquals("30", portfolioDto.getStocks().get("HSBC"), "Number of stocks should be equal to 30");

    }

    @Test
    void findAllPortfoliosTest() {

        //when
        List<PortfolioDto> portfolioDtos = assertDoesNotThrow(() -> portfolioService.getAllPortfolios(), "Should not throw any exceptions.");

        //then
        Assertions.assertNotNull(portfolioDtos, "Portfolios should not be null.");
        assertFalse(portfolioDtos.isEmpty(), "Portfolios size should be more than 0.");

    }

    @Test
    void addStockCorrectlyTest() {

        //given
        var map = portfolioDto.getStocks();

        //when
        assertDoesNotThrow(() -> portfolioService.addStock(map, "20", "HSBC"), "Should not throw any exceptions.");

        //then
        assertEquals("30", map.get("HSBC"), "Number of stocks should be equal.");

    }

    @Test
    void addStockShouldThrowInsufficientStockExceptionTest() {

        var portfolioStocks = portfolioDto.getStocks();

        assertThrows(InsufficientStockException.class, () -> portfolioService.addStock(portfolioStocks, "-11", "AAPL"));
    }

    @Test
    void parseIfContainsSymbol() {

        assertEquals(10, portfolioService.parseIfContainsSymbol(portfolioDto.getStocks(), "AAPL"));
    }

    @Test
    void parseIfContainsSymbolShouldReturn0IfSymbolDoesNotExist() {

        assertEquals(0, portfolioService.parseIfContainsSymbol(portfolioDto.getStocks(), "TTWO"));
    }

    @Test
    void parseIfContainsSymbolShouldThrowResourceNotFoundExceptionIfSymbolIsNull() {

        var portfolioStocks = portfolioDto.getStocks();

        assertThrows(ResourceNotFoundException.class, () -> portfolioService.parseIfContainsSymbol(portfolioStocks, null));
    }

    @Test
    void countBalanceTest() {

        // given
        var portfolio = portfolioMapper.toEnity(portfolioDto);

        //when
        double balance = portfolioService.countBalance(portfolio);

        //then
        assertEquals(6394.30, balance);
    }

    @Test
    void trackFundsSpentOnStocks() {

        //to be written
    }
}
