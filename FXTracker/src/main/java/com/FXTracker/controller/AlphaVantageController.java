package com.FXTracker.controller;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.service.AlphaVantageService;
import com.FXTracker.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alpha")
@Slf4j
class AlphaVantageController {

    private final AlphaVantageService alphaVantageService;
    private final StockService stockService;

    @GetMapping("/{ticker}")
    public ResponseEntity<StockDto> getStockData(@PathVariable String ticker) {

        var stock = alphaVantageService.getSingleStockDataFromAPI(ticker);

        if (stockService.stockExistsInDatabase(ticker)) {

            stockService.updateStock(ticker, stock);
        } else {
            stockService.addStock(stock);
        }

        return ResponseEntity.ok(stock);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<StockDto.StockSearchDto>> getStocksByKeyword(@PathVariable String keyword) {

        List<StockDto.StockSearchDto> stocks = alphaVantageService.findAllStocksByKeywordInAPI(keyword);

        return ResponseEntity.ok(stocks);

    }

}