package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.utils.DataTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class StockServiceTest {

    private final String EXISTING_STOCK = "TTWO";
    private final String NON_EXISTING_STOCK = "XJDSJSDJAKXAAAPOO";
    private StockDto stock;

    @Autowired
    private StockService stockService;

    @Test
    void getStockFromDBTest() {

        //given
        stock = stockService.getStock(EXISTING_STOCK);

        //then
        assertDoesNotThrow(() -> stockService.getStock(EXISTING_STOCK), "Should not throw any exceptions");
        assertNotNull(stock, "Stock should not be null.");

    }

    @Test
    void getNonExistingStockFromDBTest() {

        assertThrows(StockNotFoundException.class, () -> stockService.getStock(NON_EXISTING_STOCK));

    }

    //todo IT
    @Test
    void addStockTest() {

        stock = assertDoesNotThrow(() -> stockService.addStock(stock), "Should not throw any exceptions.");
        assertNotNull(stock, "Stock should not be null.");

    }

    //todo IT
    @Test
    void updateStockTest() {

        //given
        var entityStock = stockService.getStock("TTWO");

        //when
        stock = stockService.updateStock("TTWO", DataTest.createStock());

        //then
        assertNotNull(stock, "Stock should not be null.");
        assertEquals(entityStock.getId(), stock.getId());

    }

    //todo Mock
    @Test
    void findAllStocksTest() {

        //given
        List<StockDto> stocks = stockService.findAllStocks();

        //then
        assertDoesNotThrow(() -> stockService.findAllStocks(), "Should not throw any exceptions.");
        assertNotNull(stocks, "Stocks should not be null.");
        assertFalse(stocks.isEmpty(), "Stocks should not be empty");

    }


}
