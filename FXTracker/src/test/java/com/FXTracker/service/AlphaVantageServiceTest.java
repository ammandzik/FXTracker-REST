package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.exception.StockNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@RunWith(SpringRunner.class)
public class AlphaVantageServiceTest {

    private final String EXISTING_STOCK = "TTWO";
    private final String NON_EXISTING_STOCK = "XJDSJSDJAKXAAAPOO";
    private final String KEYWORD = "TWO";

    @Autowired
    private WebClient webClient;
    @Autowired
    private AlphaVantageService alphaVantageService;

    @Test
    public void getExistingStockDataCorrectlyTest() {

        //given

        StockDto stock = alphaVantageService.getSingleStockDataFromAPI(EXISTING_STOCK);

        //then

        assertNotNull(stock, "Stock should not be null.");

    }

    @Test
    public void nonExistingStockShouldThrowStockNotFoundException() {

        assertThrows(StockNotFoundException.class, () -> alphaVantageService.getSingleStockDataFromAPI(NON_EXISTING_STOCK));

    }

    @Test
    public void findAllStocksByKeywordCorrectlyInAPI() {

        List<StockDto.StockSearchDto> stocksFound = alphaVantageService.findAllStocksByKeywordInAPI(KEYWORD);

        assertFalse("Stocks should not be empty.", stocksFound.isEmpty());
        assertNotNull(stocksFound, "Stocks should not be null.");
    }

    @Test
    public void stocksWereNotFoundWithGivenKeyword() {

        assertThrows(StockNotFoundException.class, () -> alphaVantageService.findAllStocksByKeywordInAPI(NON_EXISTING_STOCK));


    }


}
