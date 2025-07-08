package com.FXTracker.utils;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.model.Portfolio;
import com.FXTracker.model.User;
import com.FXTracker.model.Wallet;

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

    public static Wallet createWallet(){

        return Wallet.builder()
                .id("1")
                .userId("1")
                .balance(300000d)
                .currency("USD")
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

    public static Portfolio createPortfolioDto(Map<String, String> stocks, String userId, Double balance, Double profit, Double loss, Double fundsSpent) {

        return Portfolio.builder()
                .userId(DataTest.createUser().getId())
                .stocks(stocks)
                .userId(userId)
                .balance(balance)
                .profit(profit)
                .loss(loss)
                .fundsSpent(fundsSpent)
                .build();
    }


}