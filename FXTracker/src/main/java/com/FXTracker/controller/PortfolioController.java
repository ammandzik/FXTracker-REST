package com.FXTracker.controller;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.model.Portfolio;
import com.FXTracker.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
@RestController
class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<Portfolio> createNewPortfolio(@Valid @RequestBody PortfolioDto portfolio) {

        return new ResponseEntity<>(portfolioService.createPortfolio(portfolio), HttpStatus.CREATED);

    }

    //todo when security applied, should find id of logged in user
    @PutMapping("/trade")
    public ResponseEntity<Portfolio> tradeStocks(@RequestParam String userId, @RequestParam String symbol, @RequestParam String quantity) {

        return new ResponseEntity<>(portfolioService.updateStocksInPortfolio(userId, symbol, quantity), HttpStatus.ACCEPTED);

    }

    @GetMapping("/list")
    public ResponseEntity<List<PortfolioDto>> getAllPortfolios() {

        List<PortfolioDto> portfolioList = portfolioService.getAllPortfolios();

        return ResponseEntity.ok(portfolioList);
    }

}



