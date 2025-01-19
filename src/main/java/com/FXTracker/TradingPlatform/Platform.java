package com.FXTracker.TradingPlatform;

import com.FXTracker.asset.model.Stock;
import com.FXTracker.client.model.Client;

import java.util.List;
import java.util.Set;

public class Platform {

    private Long id;
    private String platformName;
    private List<Client> clients;
    private Set<Stock> stocks;
}
