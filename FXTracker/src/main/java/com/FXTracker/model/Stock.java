package com.FXTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stock")
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("01. symbol")
    private String symbol;
    @JsonProperty("02. open")
    private String latestTradingDay;
    @JsonProperty("05. price")
    private String price;
    @JsonProperty("10. change percent")
    private String changePercent;

    public Stock(String symbol) {

        this.symbol = symbol;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
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
