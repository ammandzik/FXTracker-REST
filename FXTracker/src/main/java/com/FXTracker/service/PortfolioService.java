package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.repository.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;

    public Portfolio createPortfolio(Portfolio portfolio) {

        portfolioRepository.save(portfolio);
        return portfolio;
    }

    public PortfolioDto portfolioById(Long id) {

        return portfolioRepository
                .findById(id)
                .map(portfolioMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Portfolio not found with Id: %s", id)));

    }

    public Portfolio updatePortfolio(PortfolioDto portfolioDto) {

        portfolioRepository.findByUserId(portfolioDto.getId()).ifPresent(portfolio -> {

            portfolio.setBalance(portfolioDto.getBalance());
            portfolio.setProfit(portfolioDto.getProfit());
            portfolio.setLoss(portfolioDto.getLoss());
            portfolio.setStocks(portfolioDto.getStocks());

            portfolioRepository.save(portfolio);

        });

        return portfolioMapper.toEnity(portfolioDto);
    }

    public List<PortfolioDto> getAllPortfolios() {

        return portfolioRepository.findAll()
                .stream()
                .map(portfolio -> portfolioMapper.toDto(portfolio))
                .toList();
    }

    public Portfolio findUserPortfolio(Long userId) {

        return portfolioRepository.findByUserId(userId).get();
    }


}
