package com.FXTracker.wallet.model;

import com.FXTracker.asset.model.Asset;
import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Data
@Builder
public class Wallet {

    private Long id;
    private Currency currency;
    private Double balance;
    private Set<Asset> assets;
    private Double profit;
    private Double loss;

}
