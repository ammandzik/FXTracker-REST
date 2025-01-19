package com.FXTracker.wallet.model;

import com.FXTracker.asset.model.Stock;
import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Data
@Builder
public class Wallet {

    private Long id;
    private Currency currency;
    private Double balance;
    private Set<Stock> stocks;
    private Double profit;
    private Double loss;

}
