package com.FXTracker.controller;

import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public String getStockData(@PathVariable String ticker) {

        var stock = stockService.getSingleStockData(ticker);

        return stock;
    }

}

