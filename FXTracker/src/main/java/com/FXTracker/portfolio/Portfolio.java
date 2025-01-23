package com.FXTracker.portfolio;

import com.FXTracker.asset.Stock;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Portfolio {

    private Long id;
    private List<Stock> stocks;
    private BigDecimal balance;
    private BigDecimal profit;
    private BigDecimal loss;

}
