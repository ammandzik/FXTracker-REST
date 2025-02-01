package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.repository.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    PortfolioMapper portfolioMapper;

    public Portfolio createPortfolio(PortfolioDto portfolioDto) {

        return portfolioMapper.toEnity(portfolioDto);
    }

    public PortfolioDto portfolioById(Long id) {

        return portfolioRepository
                .findById(id)
                .map(portfolioMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Portfolio not found with Id: %s", id)));

    }

    public Portfolio updatePortfolio(Long id) {

        var updated = findUserPortfolio(id);

        portfolioRepository.save(updated);

        return updated;
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
