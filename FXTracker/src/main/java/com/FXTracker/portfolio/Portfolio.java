package com.FXTracker.portfolio;

import com.FXTracker.asset.Stock;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Stock> stocks;
    private BigDecimal balance;
    private BigDecimal profit;
    private BigDecimal loss;

}
