package com.FXTracker.mapper;

import com.FXTracker.model.StockWrapper;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;

@Component
public class StockMapper {

    public static Stock toStock(StockWrapper stockWrapper){

        var stock = new Stock(stockWrapper.getSymbol());
        stock.setName(stockWrapper.getName());
        stock.setCurrency(stockWrapper.getCurrency());
        stock.setStockExchange(stockWrapper.getStockExchange());

        return stock;

    }

    public static StockWrapper toStockWrapper(Stock stock){

        var stockWrapper = new StockWrapper(stock.getSymbol(), stock.getName());
        stockWrapper.setCurrency(stock.getCurrency());
        stockWrapper.setStockExchange(stock.getStockExchange());

        return stockWrapper;

    }



}
