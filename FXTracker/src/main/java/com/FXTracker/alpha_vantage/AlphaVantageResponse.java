package com.FXTracker.alpha_vantage;

import com.FXTracker.model.Stock;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AlphaVantageResponse {

    @JsonProperty("Global Quote")
    private Stock stock;

}
