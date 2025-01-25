package com.FXTracker.controller;

import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.exception.StockServiceException;
import com.FXTracker.model.StockWrapper;
import com.FXTracker.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stocks/{ticker}")
    public ResponseEntity<StockWrapper> getStock(@PathVariable String ticker) {

        try {
            var stock = stockService.findStock(ticker);
            return ResponseEntity.ok(stock);

        } catch (StockNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (StockServiceException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
