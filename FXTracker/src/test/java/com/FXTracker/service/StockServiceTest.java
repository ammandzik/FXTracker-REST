package com.FXTracker.service;

import com.FXTracker.ContainerBase;
import com.FXTracker.DTO.StockDto;
import com.FXTracker.utils.DataTest;
import com.FXTracker.exception.StockNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = {ContainerBase.class})
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

        assertDoesNotThrow(() -> stockService.addStock(stock), "Should not throw any exceptions.");
        assertNotNull(stock, "Stock should not be null.");

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
        assertDoesNotThrow(() -> stockService.findAllStocks(), "Should not throw any exceptions.");
        assertNotNull(stocks, "Stocks should not be null.");
        assertFalse("Stocks should not be empty", stocks.isEmpty());

    }


}
