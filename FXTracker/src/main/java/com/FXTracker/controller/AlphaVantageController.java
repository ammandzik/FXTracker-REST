package com.FXTracker.controller;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.service.AlphaVantageService;
import com.FXTracker.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alpha")
class AlphaVantageController {

    private final AlphaVantageService alphaVantageService;
    private final StockService stockService;

    @GetMapping("/{ticker}")
    public ResponseEntity<StockDto> getStockData(@PathVariable String ticker) {
        var stock = alphaVantageService.getSingleStockDataFromAPI(ticker).block();

        if (stockService.stockExistsInDatabase(ticker)) {

            stockService.updateStock(ticker, stock);
        } else {
            stockService.addStock(stock);
        }

        return ResponseEntity.ok(stock);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<Flux<List<StockDto.StockSearchDto>>> getStocksByKeyword(@PathVariable String keyword) {

        Flux<List<StockDto.StockSearchDto>> stocks = alphaVantageService.findAllStocksByKeywordInAPI(keyword);

        return ResponseEntity.ok(stocks);

    }

}