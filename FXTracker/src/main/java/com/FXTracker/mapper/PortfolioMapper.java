package com.FXTracker.mapper;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.model.Portfolio;
import com.FXTracker.model.User;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapper {
    public PortfolioDto toDto(Portfolio portfolio) {

        return PortfolioDto.builder()
                .id(portfolio.getId())
                .userId(portfolio.getUser().getId())
                .name(portfolio.getUser().getName())
                .surname(portfolio.getUser().getSurname())
                .email(portfolio.getUser().getEmail())
                .password(portfolio.getUser().getPassword())
                .stocks(portfolio.getStocks())
                .balance(portfolio.getBalance())
                .profit(portfolio.getProfit())
                .loss(portfolio.getLoss())
                .build();
    }

    public Portfolio toEnity(PortfolioDto portfolioDto) {

        return Portfolio.builder()
                .id(portfolioDto.getId())
                .user(User.builder()
                        .id(portfolioDto.getUserId())
                        .name(portfolioDto.getName())
                        .surname(portfolioDto.getSurname())
                        .email(portfolioDto.getEmail())
                        .password(portfolioDto.getPassword())
                        .build())
                .stocks(portfolioDto.getStocks())
                .balance(portfolioDto.getBalance())
                .profit(portfolioDto.getProfit())
                .loss(portfolioDto.getLoss())
                .build();
    }
}
