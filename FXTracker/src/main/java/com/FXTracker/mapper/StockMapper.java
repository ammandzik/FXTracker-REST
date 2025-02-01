package com.FXTracker.mapper;

import com.FXTracker.model.Stock;
import com.FXTracker.DTO.StockDto;
import org.springframework.stereotype.Component;
@Component
public class StockMapper {

    public StockDto toDto(Stock stock){

        return StockDto.builder()
                .id(stock.getId())
                .symbol(stock.getSymbol())
                .price(stock.getPrice())
                .latestTradingDay(stock.getLatestTradingDay())
                .changePercent(stock.getChangePercent())
                .build();

    }

    public Stock toStock(StockDto stockDto){

        return Stock.builder()
                .id(stockDto.getId())
                .symbol(stockDto.getSymbol())
                .price(stockDto.getPrice())
                .latestTradingDay(stockDto.getLatestTradingDay())
                .changePercent(stockDto.getChangePercent())
                .build();
    }
}
