package com.FXTracker.DTO;

import com.FXTracker.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.TreeMap;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDto {

    private Long id;
    private Long userId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private TreeMap<Stock, Long> stocks;
    private BigDecimal balance;
    private BigDecimal profit;
    private BigDecimal loss;

}
