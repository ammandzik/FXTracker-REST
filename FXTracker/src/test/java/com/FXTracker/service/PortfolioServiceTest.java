package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.exception.InsufficientStockException;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.repository.PortfolioRepository;
import com.FXTracker.utils.DataTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {
    private static HashMap<String, String> stocks;
    private static PortfolioDto portfolioDto;
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

        portfolioDto = DataTest.testPortfolio(stocks, 0d, 0d, 0d);
    }

    //todo IT
    @Test
    void shouldCreatePortfolioCorrectlyIT() {

        //when
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

        when(portfolioRepository.findAll()).thenReturn(DataTest.createPortfolioList());

        //then
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
