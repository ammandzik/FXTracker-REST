package com.FXTracker.service;

import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.model.Stock;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class StockServiceTest {

    private final String EXISTING_STOCK = "TTWO";
    private final String NON_EXISTING_STOCK = "XJDSJSDJAKXAAAPOO";

    @Autowired
    private StockService stockService;

    @Test
    void getExistingStockDataCorrectlyTest() {

        Stock stock = assertDoesNotThrow(() -> stockService.getSingleStockData(EXISTING_STOCK), "Method should not throw any exception.");
        assertNotNull(stock, "Stock should not be null.");

    }

    @Test
    void nonExistingStockShouldThrowStockNotFoundException() {

        assertThrows(StockNotFoundException.class, () -> stockService.getSingleStockData(NON_EXISTING_STOCK));

    }
}
