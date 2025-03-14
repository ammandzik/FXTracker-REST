package com.FXTracker.alpha_vantage;

import com.FXTracker.model.Stock;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlphaVantageResponse(
        @JsonProperty("Global Quote") Stock stock,
        @JsonProperty("bestMatches") List<Stock.StockSearch> stocks
) {}
