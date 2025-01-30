package com.FXTracker.controller;

import com.FXTracker.model.Stock;
import com.FXTracker.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Stock> getStockData(@PathVariable String ticker) {

        var stock = stockService.getSingleStockData(ticker);

        return ResponseEntity.ok(stock);
    }

}

