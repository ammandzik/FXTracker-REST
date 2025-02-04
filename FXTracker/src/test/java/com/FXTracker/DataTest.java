package com.FXTracker;

import com.FXTracker.model.Portfolio;
import com.FXTracker.model.Stock;
import com.FXTracker.model.User;

import java.util.HashMap;
import java.util.TreeMap;

public class DataTest {

    public static User createUser(){

        return User.builder()
                .name("Ola")
                .surname("Fasola")
                .email("ola221@gmail.com")
                .password("Puszolinda23422")
                .build();
    }

    public static Stock createStock(){

        return Stock.builder()
                .id(1L)
                .symbol("TTWO")
                .price("185.60")
                .changePercent("0.66")
                .build();
    }

    public static Portfolio testPortfolio(HashMap<String, Long> stocks){

        return Portfolio.builder()
                .user(DataTest.createUser())
                .stocks(stocks)
                .balance(1500.00F)
                .profit(300.00F)
                .loss(0.F)
                .build();
    }
}
