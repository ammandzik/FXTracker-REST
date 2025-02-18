package com.FXTracker.utils;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.DTO.StockDto;
import com.FXTracker.model.User;

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
                .symbol("TTWO")
                .price("185.60")
                .changePercent("0.66")
                .build();
    }

    public static PortfolioDto testPortfolio(Map<String, String> stocks, Double balance, Double profit, Double loss) {

        return PortfolioDto.builder()
                .userId(DataTest.createUser().getId())
                .stocks(stocks)
                .balance(balance)
                .profit(profit)
                .loss(loss)
                .build();
    }

}
