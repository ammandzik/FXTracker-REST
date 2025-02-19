package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.exception.StockNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertFalse;

@SpringBootTest
class AlphaVantageServiceTest {

    private final String EXISTING_STOCK = "TTWO";
    private final String NON_EXISTING_STOCK = "XJDSJSDJAKXAAAPOO";
    private final String KEYWORD = "TWO";

    @Autowired
    private WebClient webClient;
    @Autowired
    private AlphaVantageService alphaVantageService;

    @Test
    void getExistingStockDataCorrectlyTest() {

        //given

        StockDto stock = alphaVantageService.getSingleStockDataFromAPI(EXISTING_STOCK);

        //then

        assertNotNull(stock, "Stock should not be null.");

    }

    @Test
    void nonExistingStockShouldThrowStockNotFoundException() {

        assertThrows(StockNotFoundException.class, () -> alphaVantageService.getSingleStockDataFromAPI(NON_EXISTING_STOCK));

    }

    @Test
    void findAllStocksByKeywordCorrectlyInAPI() {

        List<StockDto.StockSearchDto> stocksFound = alphaVantageService.findAllStocksByKeywordInAPI(KEYWORD);

        assertFalse("Stocks should not be empty.", stocksFound.isEmpty());
        assertNotNull(stocksFound, "Stocks should not be null.");
    }

    @Test
    public void stocksWereNotFoundWithGivenKeyword() {

        assertThrows(StockNotFoundException.class, () -> alphaVantageService.findAllStocksByKeywordInAPI(NON_EXISTING_STOCK));


    }


}
