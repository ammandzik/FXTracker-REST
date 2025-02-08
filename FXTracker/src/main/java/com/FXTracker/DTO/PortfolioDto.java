package com.FXTracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDto {

    private String id;
    private String userId;
    private Map<String, String> stocks;
    private Float balance;
    private Float profit;
    private Float loss;

}
