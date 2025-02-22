package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.config.TestConfig;
import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.utils.DataTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Testcontainers
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ustawienie kolejności
class StockServiceTest2 {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StockService stockService;

    private final String EXISTING_STOCK = "TTWO";
    private final String NON_EXISTING_STOCK = "XJDSJSDJAKXAAAPOO";
    private StockDto stock;

    @BeforeAll
    void setUp() {
        mongoTemplate.getDb().drop();
    }

    @Test
    @Order(1)
    void testMongoDBConnection() {
        Assertions.assertFalse((mongoTemplate.getDb().getName()).isEmpty());
    }

    @Test
    @Order(2)
    void addStockTest() {
        stock = stockService.addStock(DataTest.createStock());
        assertNotNull(stock, "Stock should not be null.");
    }

    @Test
    @Order(3)
    void getNonExistingStockFromDBTest() {
        assertThrows(StockNotFoundException.class, () -> stockService.getStock(NON_EXISTING_STOCK));
    }

    @Test
    @Order(4)
    void getStockFromDBTest() {
        stock = stockService.getStock(EXISTING_STOCK);
        assertDoesNotThrow(() -> stockService.getStock(EXISTING_STOCK), "Should not throw any exceptions");
        assertNotNull(stock, "Stock should not be null.");
    }

    @Test
    @Order(5)
    void updateStockTest() {
        var entityStock = stockService.getStock("TTWO");
        stock = stockService.updateStock("TTWO", DataTest.createStock());
        assertNotNull(stock, "Stock should not be null.");
        Assertions.assertEquals(entityStock.getId(), stock.getId());
    }


}
