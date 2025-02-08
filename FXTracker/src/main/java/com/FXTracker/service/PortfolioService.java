package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.repository.PortfolioRepository;
import com.FXTracker.repository.StockRepository;
import com.FXTracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final UserRepository userRepository;

    public PortfolioDto createPortfolio(Portfolio portfolio) {

        HashMap<String, String> stocks = new HashMap<>();
        portfolio.setStocks(stocks);

        portfolioRepository.save(portfolio);

        return portfolioMapper.toDto(portfolio);
    }

    public PortfolioDto portfolioByUserId(String userId) {

        return portfolioRepository
                .findByUserId(userId)
                .map(portfolioMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Portfolio not found with Id: %s", userId)));
    }

    public void updatePortfolio(String id, Portfolio portfolio) {

    }

    public void addStockToPortfolio(Long id, Portfolio portfolio, String symbol, Long quantity) {


    }

    public List<PortfolioDto> getAllPortfolios() {

        return portfolioRepository.findAll().isEmpty() ? new ArrayList<>() : portfolioRepository.findAll()
                .stream()
                .map(portfolioMapper::toDto)
                .toList();
    }


}
