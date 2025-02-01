package com.FXTracker.controller;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.model.Portfolio;
import com.FXTracker.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
@RestController
class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping("/create")
    public ResponseEntity<Portfolio> createNewPortfolio(@RequestBody PortfolioDto portfolioDto) {

        return ResponseEntity.ok(portfolioService.createPortfolio(portfolioDto));

    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable Long userId) {

        return ResponseEntity.ok(portfolioService.updatePortfolio(userId));

    }


}



