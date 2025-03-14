package com.FXTracker.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * RepresentsPortfolio DTO with details such as id, user id, stocks, balance, profit, and loss.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDto {

    private String id;
    @NotNull
    private String userId;
    private Map<String, String> stocks;
    private Double balance;
    private Double fundsSpent;
    private Double profit;
    private Double loss;

}
