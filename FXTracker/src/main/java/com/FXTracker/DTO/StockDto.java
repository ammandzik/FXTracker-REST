package com.FXTracker.DTO;

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
