package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.repository.PortfolioRepository;
import com.FXTracker.repository.StockRepository;
import com.FXTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;

    public Portfolio createPortfolio(PortfolioDto portfolioDto) {

        Map<String, String> stocks = new HashMap<>();

        var entity = portfolioMapper.toEnity(portfolioDto);

        entity.setStocks(stocks);

        portfolioRepository.save(entity);

        return entity;
    }

    public PortfolioDto portfolioByUserId(String userId) {

        return portfolioRepository.findByUserId(userId)
                .map(portfolioMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Portfolio not found with Id: %s", userId)));
    }

    @Transactional
    public Portfolio updatePortfolio(String userId, String symbol, String quantity) {

        var portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Portfolio not found for user id: %s ", userId)));

        Map<String, String> stocks;

        try {
            stocks = portfolio.getStocks();

        } catch (NullPointerException ex) {
            throw new ResourceNotFoundException(String.format("No stocks were found for portfolio ID: %s", portfolio.getId()));
        }

        int owned = Integer.parseInt(stocks.get(symbol));
        int value = Integer.parseInt(quantity);

        int amount = stocks.containsKey(symbol) ? owned + value : value;

        portfolio.getStocks().put(symbol, String.valueOf(amount));

        return portfolioRepository.save(portfolio);
    }

    public List<PortfolioDto> getAllPortfolios() {

        List<Portfolio> portfolios = portfolioRepository.findAll();

        if (portfolios.isEmpty()) {
            throw new ResourceNotFoundException("No portfolios were found.");
        }
        return portfolios
                .stream()
                .map(portfolioMapper::toDto)
                .toList();
    }


}
