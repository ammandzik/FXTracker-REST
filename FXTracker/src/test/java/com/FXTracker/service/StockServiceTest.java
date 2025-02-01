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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StockServiceTest {

    private final String EXISTING_STOCK = "TTWO";
    private final String NON_EXISTING_STOCK = "XJDSJSDJAKXAAAPOO";
    private final String KEYWORD = "TWO";

    @Autowired
    private StockService stockService;

    @Test
    public void getExistingStockDataCorrectlyTest() {

        StockDto stock = stockService.getSingleStockData(EXISTING_STOCK);

        assertNotNull(stock, "Stock should not be null.");


    }

    @Test
    public void nonExistingStockShouldThrowStockNotFoundException() {

        assertThrows(StockNotFoundException.class, () -> stockService.getSingleStockData(NON_EXISTING_STOCK));

    }

    @Test
    public void findAllStocksByKeywordCorrectly() {
        List<StockDto.StockSearchDto> stocksFound = stockService.findAllStocksByKeyword(KEYWORD);

        assertThat(!stocksFound.isEmpty());
        assertNotNull(stocksFound);

    }
}
