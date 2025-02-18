package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.exception.InsufficientStockException;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.utils.DataTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PortfolioServiceTest {

    private static HashMap<String, String> stocks = new HashMap<>();
    private static PortfolioDto portfolioDto;

    @BeforeAll
    static void addToMap() {

        stocks.put("AAPL", "10");
        stocks.put("HSBC", "10");
        stocks.put("TSLA", "10");

        portfolioDto = DataTest.testPortfolio(stocks, 0d, 0d, 0d);
    }

    @Mock
    private PortfolioService portfolioService;
    @InjectMocks
    private PortfolioMapper portfolioMapper;


    //todo IT
    @Test
    public void shouldCreatePortfolioCorrectlyIT() {

        //when
        var portfolio = assertDoesNotThrow(() -> portfolioService.createPortfolio(portfolioDto), "Should not throw any exceptions.");

        //then
        assertNotNull(portfolio, "Portfolio should not be null");
    }

    //todo write test for update
    @Test
    public void shouldUpdatePortfolioCorrectlyIT() {

        Mockito.when(portfolioService.portfolioByUserId("1")).thenReturn(portfolioDto);

        Assertions.assertNotNull(portfolioDto, "Portfolio should not be null.");
        assertEquals("ID's should be equal.", portfolioDto.getId());

    }

    @Test
    public void findAllPortfoliosTest() {

        //then
        List<PortfolioDto> portfolioDtos = assertDoesNotThrow(() -> portfolioService.getAllPortfolios(), "Should not throw any exceptions.");
        Assertions.assertNotNull(portfolioDtos, "Portfolios should not be null.");
        assertFalse(portfolioDtos.isEmpty(), "Portfolios size should be more than 0.");

    }

    @Test
    public void addStockCorrectlyTest() {

        var portfolio = portfolioMapper.toEnity(portfolioDto);

        assertDoesNotThrow(() -> portfolioService.addStock(portfolio, "20", "HSBC"), "Should not throw any exceptions.");
        assertEquals("30", portfolio.getStocks().get("HSBC"), "Number of stocks should be equal.");

    }

    @Test
    public void addStockShouldThrowInsufficientStockExceptionTest() {


        assertThrows(InsufficientStockException.class, () -> portfolioService.addStock(portfolioMapper.toEnity(portfolioDto), "-11", "AAPL"));
    }

    @Test
    public void parseIfContainsSymbol() {

        assertEquals(10, portfolioService.parseIfContainsSymbol(portfolioMapper.toEnity(portfolioDto), "AAPL"));
    }

    @Test
    public void parseIfContainsSymbolShouldReturn0IfSymbolDoesNotExist() {

        assertEquals(0, portfolioService.parseIfContainsSymbol(portfolioMapper.toEnity(portfolioDto), "TTWO"));
    }

    @Test
    public void parseIfContainsSymbolShouldThrowResourceNotFoundExceptionIfSymbolIsNull() {

        assertThrows(ResourceNotFoundException.class, () -> portfolioService.parseIfContainsSymbol(portfolioMapper.toEnity(portfolioDto), null));
    }

    @Test
    public void countBalance() {
    }

    @Test
    public void trackFundsSpentOnStocks() {


    }
}
