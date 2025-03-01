package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.model.Stock;
import com.FXTracker.utils.DataTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class StockServiceTest {

    private final String EXISTING_STOCK = "TTWO";
    private final String NON_EXISTING_STOCK = "XJDSJSDJAKXAAAPOO";
    private StockDto stock;
    @Autowired
    private StockService stockService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void shouldInsertStocksIntoDb() {

        mongoTemplate.save(new Stock("1", "HSBC", "56.08", "56.08", "0,20", null), "stocks");
        mongoTemplate.save(new Stock("2", "TTWO", "211.65", "211.65", "-1.67", null), "stocks");
        mongoTemplate.save(new Stock("3", "TSLA", "337.80", "337.80", "-4.68", null), "stocks");

    }

    @AfterEach
    public void cleanUpMongoDB() {

        mongoTemplate.dropCollection(Stock.class);
    }

    @Test
    void getStockFromDBTest() {

        //when
        stock = assertDoesNotThrow(() -> stockService.getStock(EXISTING_STOCK), "Should not throw any exceptions");

        //then
        assertNotNull(stock, "Stock should not be null.");

    }

    @Test
    void getNonExistingStockFromDBTest() {

        assertThrows(StockNotFoundException.class, () -> stockService.getStock(NON_EXISTING_STOCK), "Should throw Stock Not Found Exception while fetching not existing symbol");

    }

    @Test
    void addStockTest() {

        //given
        stock = DataTest.createStock();

        //when
        stock = assertDoesNotThrow(() -> stockService.addStock(stock), "Should not throw any exceptions.");

        //then
        assertNotNull(stock, "Stock should not be null.");

    }

    @Test
    void updateStockTest() {

        //given
        var entityStock = stockService.getStock("TTWO");

        //when
        stock = stockService.fetchUpdatedStock("TTWO");

        //then
        assertNotNull(stock, "Stock should not be null.");
        assertEquals(entityStock.getId(), stock.getId(), "ID's of stocks should be equal.");

    }

    @Test
    void findAllStocksTest() {

        //when
        List<StockDto> stocks = assertDoesNotThrow(() -> stockService.findAllStocks(), "Should not throw any exceptions.");

        //then
        assertNotNull(stocks, "Stocks should not be null.");
        assertFalse(stocks.isEmpty(), "Stocks should not be empty");

    }


}
