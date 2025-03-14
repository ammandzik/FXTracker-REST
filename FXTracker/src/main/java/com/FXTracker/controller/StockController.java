package com.FXTracker.controller;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
class StockController {

    private final StockService stockService;

    @GetMapping("/{symbol}")
    public ResponseEntity<StockDto> getStockBySymbol(@PathVariable String symbol) {

        var stock = stockService.getStock(symbol);

        return ResponseEntity.ok(stock);

    }

    @GetMapping("/list")
    public ResponseEntity<List<StockDto>> getAllStocks() {

        List<StockDto> stockDtos = stockService.findAllStocks();

        return ResponseEntity.ok(stockDtos);
    }

}

