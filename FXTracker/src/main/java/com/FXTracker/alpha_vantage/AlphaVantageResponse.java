package com.FXTracker.alpha_vantage;

import com.FXTracker.model.Stock;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaVantageResponse {

    @JsonProperty("Global Quote")
    private Stock stock;

    @JsonProperty("bestMatches")
    private List<Stock.StockSearch> stocks;

}
