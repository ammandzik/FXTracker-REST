package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.model.User;
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
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final UserRepository userRepository;

    public PortfolioDto createPortfolio(Portfolio portfolio) {

        HashMap<String, Long> stocks = new HashMap<>();
        portfolio.setStocks(stocks);

        portfolioRepository.save(portfolio);

        return portfolioMapper.toDto(portfolio);
    }

    public PortfolioDto portfolioByUserId(Long userId) {

        return portfolioRepository
                .findByUserId(userId)
                .map(portfolioMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Portfolio not found with Id: %s", userId)));
    }

    public PortfolioDto updatePortfolio(Long id, Portfolio portfolio) {

        var p1 = portfolioRepository.findById(id).get();

        var user = userRepository.findById(p1.getUser().getId()).get();

        portfolio.setUser(user);

        portfolioRepository.save(portfolio);

        return portfolioMapper.toDto(portfolio);
    }

    public void addStockToPortfolio(Long id, Portfolio portfolio, String symbol, Long quantity) {

        Optional<Portfolio> p1 = portfolioRepository.findById(id);

        if (p1.isEmpty()) {

            throw new EntityNotFoundException(String.format("Portfolio was not found with given ID: %s ", id));
        }

        Optional<User> u1 = userRepository.findById(p1.get().getUser().getId());

        if (u1.isEmpty()) {

            throw new EntityNotFoundException(String.format("User was not found with given ID: %s ", id));
        }

        portfolio.setUser(u1.get());

        portfolio.getStocks().put(symbol, quantity);

        portfolioRepository.save(portfolio);

    }

    public List<PortfolioDto> getAllPortfolios() {

        return portfolioRepository.findAll().isEmpty() ? new ArrayList<>() : portfolioRepository.findAll()
                .stream()
                .map(portfolioMapper::toDto)
                .toList();
    }


}
