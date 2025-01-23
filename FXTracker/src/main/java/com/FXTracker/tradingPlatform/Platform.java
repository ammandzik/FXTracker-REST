package com.FXTracker.tradingPlatform;

import com.FXTracker.asset.Stock;
import com.FXTracker.user.User;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Platform {

    private Long id;
    private String platformName;
    private Set<Stock> stocks;
    private List<User> users;
}
