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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final UserRepository userRepository;

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

    public Portfolio updatePortfolio(String userId, PortfolioDto portfolioDto) {


        var portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Portfolio not found for user id: %s ", userId)));


        portfolio.setStocks(portfolioDto.getStocks());
        portfolio.setBalance(portfolioDto.getBalance());
        portfolio.setProfit(portfolio.getProfit());
        portfolio.setLoss(portfolioDto.getLoss());

        return portfolioRepository.save(portfolio);
    }

    public PortfolioDto addStockToPortfolio(String userId, PortfolioDto portfolioDto, String symbol, String quantity) {

        var portfolio = portfolioByUserId(userId);

        portfolioDto.setId(portfolio.getId());
        portfolioDto.getStocks().put(symbol, quantity);

        portfolioRepository.save(portfolioMapper.toEnity(portfolioDto));

        return portfolioDto;


    }

    public List<PortfolioDto> getAllPortfolios() {

        List<Portfolio> portfolios = portfolioRepository.findAll();

        if (portfolios.isEmpty()) {
            throw new ResourceNotFoundException("Portfolios were not found");
        }
        return portfolios
                .stream()
                .map(portfolioMapper::toDto)
                .toList();
    }


}
