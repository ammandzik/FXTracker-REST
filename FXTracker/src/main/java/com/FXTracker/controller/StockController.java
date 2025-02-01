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

    @GetMapping("/{ticker}")
    public ResponseEntity<StockDto> getStockData(@PathVariable String ticker) {

        var stock = stockService.getSingleStockData(ticker);

        return ResponseEntity.ok(stock);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<StockDto.StockSearchDto>> getStocksByKeyword(@PathVariable String keyword) {

        List<StockDto.StockSearchDto> stocks = stockService.findAllStocksByKeyword(keyword);

        return ResponseEntity.ok(stocks);


    }

}

