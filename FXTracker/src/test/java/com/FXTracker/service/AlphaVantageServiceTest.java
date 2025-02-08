package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.exception.StockNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AlphaVantageServiceTest {

    private final String EXISTING_STOCK = "TTWO";
    private final String NON_EXISTING_STOCK = "XJDSJSDJAKXAAAPOO";
    private final String KEYWORD = "TWO";
    @Autowired
    private AlphaVantageService alphaVantageService;

    @Test
    public void getExistingStockDataCorrectlyTest() {

        StockDto stock = alphaVantageService.getSingleStockDataFromAPI(EXISTING_STOCK);

        assertNotNull(stock, "Stock should not be null.");

    }

    @Test
    public void nonExistingStockShouldThrowStockNotFoundException() {

        assertThrows(StockNotFoundException.class, () -> alphaVantageService.getSingleStockDataFromAPI(NON_EXISTING_STOCK));

    }

    @Test
    public void findAllStocksByKeywordCorrectlyInAPI() {

        List<StockDto.StockSearchDto> stocksFound = alphaVantageService.findAllStocksByKeywordInAPI(KEYWORD);

        assertThat(!stocksFound.isEmpty());
        assertNotNull(stocksFound);
    }

    @Test
    public void stocksWereNotFoundWithGivenKeyword() {

        List<StockDto.StockSearchDto> noStocks = alphaVantageService.findAllStocksByKeywordInAPI(KEYWORD);

        assertThrows(StockNotFoundException.class, () -> alphaVantageService.findAllStocksByKeywordInAPI(NON_EXISTING_STOCK));

        assertNull(noStocks);

    }


}
