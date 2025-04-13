package com.FXTracker.DTO;

import jakarta.validation.constraints.NotEmpty;
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
    @NotNull(message = "User ID cannot be null")
    @NotEmpty(message = "User ID cannot be empty")
    private String userId;
    private Map<String, String> stocks;
    private Double balance;
    private Double fundsSpent;
    private Double profit;
    private Double loss;

}
