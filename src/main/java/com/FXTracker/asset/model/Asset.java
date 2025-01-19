package com.FXTracker.asset.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Asset {

    private Long id;
    private AssetType type;
    private String name;
    private Long amount;
    private Double price;


}
