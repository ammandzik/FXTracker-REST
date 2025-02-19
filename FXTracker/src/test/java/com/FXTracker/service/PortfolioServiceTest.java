package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.exception.InsufficientStockException;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.repository.PortfolioRepository;
import com.FXTracker.utils.DataTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class PortfolioServiceTest {
    private static HashMap<String, String> stocks;
    private static PortfolioDto portfolioDto;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Mock
    private PortfolioRepository portfolioRepository;
    @Mock
    private PortfolioMapper portfolioMapper;
    @Mock
    private StockService stockService;
    @InjectMocks
    private PortfolioService portfolioService;

    @BeforeAll
    static void setUp() {

        stocks = new HashMap<>();
        stocks.put("AAPL", "10");
        stocks.put("HSBC", "10");
        stocks.put("TSLA", "10");

        portfolioDto = DataTest.createPortfolioDto(stocks, "1", 0d, 0d, 0d);
    }

    @BeforeEach
    public void setUpMongoDB() {
        mongoTemplate.save(portfolioDto, "portfolios");
        mongoTemplate.save(DataTest.createPortfolioDto(stocks, "2", 20444d, 15023d, 0d), "portfolios");
        mongoTemplate.save(DataTest.createPortfolioDto(stocks, "3", 750d, 250d, 0d), "portfolios");
    }

    @AfterEach
    public void cleanUpMongoDB() {
        mongoTemplate.dropCollection(Portfolio.class);
    }

    //todo IT
    @Test
    void shouldCreatePortfolioCorrectlyIT() {

        var entity = portfolioMapper.toEnity(portfolioDto);

        when(portfolioRepository.save(entity)).thenReturn(entity);

        //given
        var portfolio = assertDoesNotThrow(() -> portfolioService.createPortfolio(portfolioDto), "Should not throw any exceptions.");

        //then
        assertNotNull(portfolio, "Portfolio should not be null");
    }

    //todo write test for update
    @Test
    void shouldUpdatePortfolioCorrectlyIT() {

        var portfolio = portfolioMapper.toEnity(portfolioDto);

        portfolioService.updatePortfolio(portfolio, "HSBC", "200");

        Assertions.assertNotNull(portfolioDto, "Portfolio should not be null.");
    }

    @Test
    void findAllPortfoliosTest() {

        when(portfolioRepository.findAll()).thenReturn(mongoTemplate.findAll(Portfolio.class, "portfolios"));

        List<PortfolioDto> portfolioDtos = assertDoesNotThrow(() -> portfolioService.getAllPortfolios(), "Should not throw any exceptions.");
        Assertions.assertNotNull(portfolioDtos, "Portfolios should not be null.");
        assertFalse(portfolioDtos.isEmpty(), "Portfolios size should be more than 0.");

    }

    @Test
    void addStockCorrectlyTest() {

        var map = portfolioDto.getStocks();

        assertDoesNotThrow(() -> portfolioService.addStock(map, "20", "HSBC"), "Should not throw any exceptions.");
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
    void countBalance() {

        // to be written
    }

    @Test
    void trackFundsSpentOnStocks() {

        //to be written
    }
}
