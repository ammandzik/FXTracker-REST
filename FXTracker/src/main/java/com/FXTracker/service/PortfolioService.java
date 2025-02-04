package com.FXTracker.service;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.repository.PortfolioRepository;
import com.FXTracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final UserRepository userRepository;

    public PortfolioDto createPortfolio(Portfolio portfolio) {

        portfolioRepository.save(portfolio);

        return portfolioMapper.toDto(portfolio);
    }

    public PortfolioDto portfolioById(Long id) {

        return portfolioRepository
                .findById(id)
                .map(portfolioMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Portfolio not found with Id: %s", id)));
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

    public List<PortfolioDto> getAllPortfolios() {

        return portfolioRepository.findAll().isEmpty() ? new ArrayList<>() : portfolioRepository.findAll()
                .stream()
                .map(portfolio -> portfolioMapper.toDto(portfolio))
                .toList();
    }


}
