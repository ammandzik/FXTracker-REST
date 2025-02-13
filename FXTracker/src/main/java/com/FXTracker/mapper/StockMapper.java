package com.FXTracker.mapper;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.model.Stock;
import org.springframework.stereotype.Component;

/**
 * Custom Mapper class for Stock and StockDto classes.
 */
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

    @Component
    public static class StockSearchMapper {

        public StockDto.StockSearchDto toDto(Stock.StockSearch stockSearch){

            return StockDto.StockSearchDto.builder()
                    .name(stockSearch.getName())
                    .symbol(stockSearch.getSymbol())
                    .currency(stockSearch.getCurrency())
                    .marketOpen(stockSearch.getMarketOpen())
                    .marketClose(stockSearch.getMarketClose())
                    .build();
        }

        public Stock.StockSearch toEntity(StockDto.StockSearchDto stockSearchDto){

            return Stock.StockSearch.builder()
                    .name(stockSearchDto.getName())
                    .symbol(stockSearchDto.getName())
                    .marketOpen(stockSearchDto.getMarketOpen())
                    .marketClose(stockSearchDto.getMarketClose())
                    .currency(stockSearchDto.getCurrency())
                    .build();
        }

    }
}
