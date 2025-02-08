package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.DataTest;
import com.FXTracker.exception.StockNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
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
    public void getStockFromDBTest() {

        //given
        stock = stockService.getStock(EXISTING_STOCK);

        //then
        assertDoesNotThrow(() -> stockService.getStock(EXISTING_STOCK), "Should not throw any exceptions");
        assertNotNull(stock, "Stock should not be null.");

    }

    @Test
    public void getNonExistingStockFromDBTest() {

        assertThrows(StockNotFoundException.class, () -> stockService.getStock(NON_EXISTING_STOCK));


    }

    //todo IT Service - test container required
    @Test
    public void addStockTest() {

        stock = stockService.addStock(DataTest.createStock());

        assertDoesNotThrow(() -> stockService.addStock(stock));
        assertNotNull(stock);

    }

    //todo IT Service - test container required
    @Test
    public void updateStockTest() {

        //given
        var entityStock = stockService.getStock("TTWO");

        //when
        stock = stockService.updateStock("TTWO", DataTest.createStock());

        //then
        assertNotNull(stock, "Stock should not be null.");
        assertEquals(entityStock.getId(), stock.getId());

    }

    @Test
    public void findAllStocksTest() {

        //given
        List<StockDto> stocks = stockService.findAllStocks();

        //then
        assertDoesNotThrow(() -> stockService.findAllStocks());
        assertNotNull(stocks);
        assertFalse(stocks.isEmpty());

    }


}
