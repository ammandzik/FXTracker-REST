package com.FXTracker.asset.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stock {

    private Long id;
    private String name;
    private Long amount;
    private Double price;


}
