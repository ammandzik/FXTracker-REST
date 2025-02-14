package com.FXTracker.DTO;

import lombok.Builder;
import lombok.Data;

/**
 * Represents Stock DTO with details such as id, symbol, latest trading day, price and change in percent.
 */
@Builder
@Data
public class StockDto {

    private String id;
    private String symbol;
    private String latestTradingDay;
    private String price;
    private String changePercent;

    /**
     * Represents StockSearch DTO with details such as symbol, name, market open, market close and currency.
     */
    @Builder
    @Data
    public static class StockSearchDto{

        private String symbol;
        private String name;
        private String marketOpen;
        private String marketClose;
        private String currency;

    }

}
