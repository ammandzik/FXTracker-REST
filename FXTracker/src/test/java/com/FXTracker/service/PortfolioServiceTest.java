package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.exception.InsufficientFundsException;
import com.FXTracker.exception.InsufficientStockException;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.model.Stock;
import com.FXTracker.model.User;
import com.FXTracker.model.Wallet;
import com.FXTracker.utils.DataTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PortfolioServiceTest {
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
        assertDoesNotThrow(() -> portfolioService.addStock(map, "20", "HSBC", "1"), "Should not throw any exceptions.");

        //then
        assertEquals("30", map.get("HSBC"), "Number of stocks should be equal.");

    }

    @Test
    void addStockShouldThrowInsufficientFundsExceptionTest() {

        //given
        var portfolioStocks = portfolio.getStocks();

        //when & then
        assertThrows(InsufficientFundsException.class, () -> portfolioService.addStock(portfolioStocks, "20000", "AAPL", "1"));

    }

    @Test
    void addStockShouldThrowInsufficientStockExceptionTest() {

        var portfolioStocks = portfolio.getStocks();

        assertThrows(InsufficientStockException.class, () -> portfolioService.addStock(portfolioStocks, "-11", "AAPL", "1"));
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
    @Order(1)
    void countBalance() {

        //when
        var balance = portfolioService.countBalance(portfolio);

        //then
        assertNotNull(balance, "Balance should not be null");
        assertEquals(6394.3, balance, "Balance should be equal for portfolio.");
        assertTrue(balance > 0, "Balance should be more than 0.");


    }

    @Test
    void countBudgetSpentTest() {

        //when
        var budget = portfolioService.countBudgetSpent(portfolio, "AAPL", "-5");

        //then - actual budget spent on owned stocks + budget spent on  recently bought stocks
        assertNotNull(budget, "Budget should not be null");
        assertEquals(-1227.75, budget, "Budget should be equal with expected.");

    }
}
