package com.FXTracker.service;

import com.FXTracker.model.StockWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Test
    void invoke() throws IOException {

        final StockWrapper stock = stockService.findStock("UU.L");
        System.out.println(stock.getStock());

        final BigDecimal price = stockService.findPrice(stock);
        System.out.println(price);

        final BigDecimal change = stockService.findPercentageChange(stock);
        System.out.println(change);

        final BigDecimal percentageMA200 = stockService.findChangeFrom200AvgPercent(stock);
        System.out.println(percentageMA200);

    }
}
