package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.exception.StockNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertFalse;

@ActiveProfiles("test")
@SpringBootTest
class AlphaVantageServiceTest {

    private final String EXISTING_STOCK = "TTWO";
    private final String NON_EXISTING_STOCK = "XJDSJSDJAKXAAAPOO";
    private final String KEYWORD = "TS";
    @Autowired
    private AlphaVantageService alphaVantageService;
    @Autowired
    private WebClient webClient;


    @Test
    void getExistingStockDataCorrectlyTest() {

        //when
        Mono<StockDto> stock = alphaVantageService.getSingleStockDataFromAPI(EXISTING_STOCK);

        //then
        assertNotNull(stock, "Stock should not be null.");

    }

    @Test
    void nonExistingStockShouldThrowStockNotFoundException() {

        assertThrows(StockNotFoundException.class, () -> alphaVantageService.getSingleStockDataFromAPI(NON_EXISTING_STOCK), "Should throw StockNotFoundException while fetching not existing stock.");

    }

    @Test
    void findAllStocksByKeywordCorrectlyInAPI() {

        //when
        List<StockDto.StockSearchDto> stocksFound = alphaVantageService.findAllStocksByKeywordInAPI(KEYWORD);

        //then
        assertFalse("Stocks should not be empty.", stocksFound.isEmpty());
        assertNotNull(stocksFound, "Stocks should not be null.");
    }

    @Test
    void stocksWereNotFoundWithGivenKeyword() {

        assertThrows(StockNotFoundException.class, () -> alphaVantageService.findAllStocksByKeywordInAPI(NON_EXISTING_STOCK), "Should throw StockNotFoundException while none of the stocks contains provided phrase.");

    }


}
