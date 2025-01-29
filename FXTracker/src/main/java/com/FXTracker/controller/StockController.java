package com.FXTracker.controller;

import com.FXTracker.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
class StockController {

    private final StockService stockService;

    @GetMapping("/{ticker}")
    public String getStockData(@PathVariable String ticker) {

        var stock = stockService.getSingleStockData(ticker);

//        if (stock.equals(null)){
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//
//        }
        return stock;
    }

}

