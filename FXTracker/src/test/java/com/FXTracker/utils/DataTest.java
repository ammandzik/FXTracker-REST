package com.FXTracker.utils;

import com.FXTracker.DTO.PortfolioDto;
import com.FXTracker.DTO.StockDto;
import com.FXTracker.mapper.PortfolioMapper;
import com.FXTracker.model.Portfolio;
import com.FXTracker.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

    public static PortfolioDto createPortfolioDto(Map<String, String> stocks, String userId, Double balance, Double profit, Double loss) {

        return PortfolioDto.builder()
                .userId(DataTest.createUser().getId())
                .stocks(stocks)
                .userId(userId)
                .balance(balance)
                .profit(profit)
                .loss(loss)
                .build();
    }

    public static List<Portfolio> createPortfolioList(){

        List<Portfolio> portfolios = List.of(
                new Portfolio("1", "1", new HashMap<>(), 100d, 100d, 100d,100d),
                new Portfolio("2", "2", new HashMap<>(), 100d, 100d, 100d,100d)

        );

        return portfolios;

    }


}
