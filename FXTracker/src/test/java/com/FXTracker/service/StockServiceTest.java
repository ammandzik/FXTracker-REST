package com.FXTracker.service;

import com.FXTracker.model.StockDto;
import org.junit.Test;
import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.model.Stock;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StockServiceTest {

    private final String EXISTING_STOCK = "TTWO";
    private final String NON_EXISTING_STOCK = "XJDSJSDJAKXAAAPOO";

    @Autowired
    private StockService stockService;

    @Test
    public void getExistingStockDataCorrectlyTest() {

        StockDto stock = stockService.getSingleStockData(EXISTING_STOCK);

        assertNotNull(stock, "Stock should not be null.");
        assertEquals(null, stock.getId());


    }
    @Test
    public void nonExistingStockShouldThrowStockNotFoundException() {

        assertThrows(StockNotFoundException.class, () -> stockService.getSingleStockData(NON_EXISTING_STOCK));

    }

    @Test
    void findAllStocksByKeywordCorrectly(){

    }
}
