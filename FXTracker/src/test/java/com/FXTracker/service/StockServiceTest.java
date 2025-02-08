package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.DataTest;
import com.FXTracker.exception.StockNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@RunWith(SpringRunner.class)
public class StockServiceTest {

    private final String EXISTING_STOCK = "TTWO";
    private final String NON_EXISTING_STOCK = "XJDSJSDJAKXAAAPOO";

    private StockDto stock;

    @Autowired
    private StockService stockService;

    @Test
    public void getStockFromDB() {

        //given
        stock = stockService.getStock(EXISTING_STOCK);

        //then
        assertDoesNotThrow(() -> stockService.getStock(EXISTING_STOCK), "Should not throw any exceptions");
        assertNotNull(stock, "Stock should not be null.");

    }

    @Test
    public void getNonExistingStockFromDB() {

        //given
        stock = stockService.getStock(NON_EXISTING_STOCK);

        //then
        assertThrows(StockNotFoundException.class,() -> stockService.getStock(EXISTING_STOCK));
        assertNotNull(stock, "Stock should not be null.");

    }

    @Test
    public void addStock(){

        stock  = stockService.addStock(DataTest.createStock());

        assertDoesNotThrow(()-> stockService.addStock(stock));
        assertNotNull(stock);

    }

    @Test
    public void updateStock(){

    }


}
