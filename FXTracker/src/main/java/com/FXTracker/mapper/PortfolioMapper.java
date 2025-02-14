package com.FXTracker.mapper;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.model.Portfolio;
import org.springframework.stereotype.Component;

/**
 * Custom Mapper class for Portfolio and PortfolioDto classes.
 */
@Component
public class PortfolioMapper {
    public PortfolioDto toDto(Portfolio portfolio) {

        return PortfolioDto.builder()
                .id(portfolio.getId())
                .userId(portfolio.getUserId())
                .stocks(portfolio.getStocks())
                .balance(portfolio.getBalance())
                .fundsSpent(portfolio.getFundsSpent())
                .profit(portfolio.getProfit())
                .loss(portfolio.getLoss())
                .build();
    }

    public Portfolio toEnity(PortfolioDto portfolioDto) {

        return Portfolio.builder()
                .id(portfolioDto.getId())
                .userId(portfolioDto.getUserId())
                .stocks(portfolioDto.getStocks())
                .fundsSpent(portfolioDto.getFundsSpent())
                .balance(portfolioDto.getBalance())
                .profit(portfolioDto.getProfit())
                .loss(portfolioDto.getLoss())
                .build();
    }
}
