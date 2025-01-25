package com.FXTracker.service;

import com.FXTracker.model.StockWrapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;

import static com.FXTracker.mapper.StockMapper.toStock;

@SpringBootTest
@RunWith(SpringRunner.class)
class StockServiceTest {


    @Autowired
    private StockService stockService;


    @Test
    void invoke() throws IOException {

        final StockWrapper stock = stockService.findStock("Ala ma kota");
        System.out.println(toStock(stock).getSymbol());

        final BigDecimal price = stockService.findPrice(stock);
        System.out.println(price);

        final BigDecimal change = stockService.findPercentageChange(stock);
        System.out.println(change);

        final BigDecimal percentageMA200 = stockService.findChangeFrom200AvgPercent(stock);
        System.out.println(percentageMA200);

    }
}
