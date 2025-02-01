package com.FXTracker.service;

import com.FXTracker.model.Portfolio;
import com.FXTracker.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;

    public Portfolio findUserPortfolio(Long userId){

        return portfolioRepository.findByUserId(userId).get();
    }


}
