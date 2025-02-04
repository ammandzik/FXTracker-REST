package com.FXTracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

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
    private HashMap<String, Long> stocks;
    private Float balance;
    private Float profit;
    private Float loss;

}
