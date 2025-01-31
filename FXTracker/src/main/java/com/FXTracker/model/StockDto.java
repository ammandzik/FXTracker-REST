package com.FXTracker.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StockDto {

    private Long id;
    private String symbol;
    private String latestTradingDay;
    private String price;
    private String changePercent;

}
