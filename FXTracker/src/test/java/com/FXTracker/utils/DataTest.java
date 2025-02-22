package com.FXTracker.utils;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.DTO.StockDto;
import com.FXTracker.model.Portfolio;
import com.FXTracker.model.Stock;
import com.FXTracker.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTest {

    public static User createUser() {

        return User.builder()
                .id("1")
                .name("Ola")
                .surname("Fasola")
                .email("ola221@gmail.com")
                .password("Puszolinda23422")
                .build();
    }

    public static StockDto createStock() {

        return StockDto.builder()
                .id(null)
                .symbol("Test Stock")
                .price("185.60")
                .changePercent("0.66")
                .build();
    }

    public static PortfolioDto createPortfolioDto(Map<String, String> stocks, String userId, Double balance, Double profit, Double loss, Double fundsSpent) {

        return PortfolioDto.builder()
                .userId(DataTest.createUser().getId())
                .stocks(stocks)
                .userId(userId)
                .balance(balance)
                .profit(profit)
                .loss(loss)
                .fundsSpent(fundsSpent)
                .build();
    }

    public static List<Portfolio> createPortfolioList() {

        return List.of(
                new Portfolio("1", "1", new HashMap<>(), 100d, 100d, 100d, 100d),
                new Portfolio("2", "2", new HashMap<>(), 100d, 100d, 100d, 100d)

        );

    }

    public static List<Stock> createTestStocksList() {

        return List.of(
                new Stock("1", "HSBC","56.08","56.08","0,20",null),
                new Stock("2", "TTWO","211.65","211.65","-1.67",null),
                new Stock("3", "TSLA","337.80","337.80","-4.68",null)

        );

    }


}
