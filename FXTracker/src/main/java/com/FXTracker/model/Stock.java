package com.FXTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "stocks")
public class Stock {

    @JsonProperty("01. symbol")
    private String symbol;
    @JsonProperty("02. open")
    private String latestTradingDay;
    @JsonProperty("05. price")
    private String price;
    @JsonProperty("10. change percent")
    private String changePercent;

    private StockSearch stockSearch;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StockSearch {

        @JsonProperty("1. symbol")
        private String symbol;
        @JsonProperty("2. name")
        private String name;
        @JsonProperty("5. marketOpen")
        private String marketOpen;
        @JsonProperty("6. marketClose")
        private String marketClose;
        @JsonProperty("8. currency")
        private String currency;
    }
}