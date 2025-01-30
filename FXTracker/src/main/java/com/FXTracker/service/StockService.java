package com.FXTracker.service;

import com.FXTracker.alpha_vantage.AlphaVantageResponse;
import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.mapper.StockMapper;
import com.FXTracker.model.Stock;
import com.FXTracker.model.StockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StockService {

    private static final String API_KEY = "IZA7PDJIYSW0RL7V";

    @Autowired
    private WebClient webClient;
    @Autowired
    private final StockMapper stockMapper;

    public StockDto getSingleStockData(String ticker) {

        var stock = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "GLOBAL_QUOTE")
                        .queryParam("symbol", ticker)
                        .queryParam("apikey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(AlphaVantageResponse.class)
                .map(AlphaVantageResponse::getStock)
                .map(stockMapper::toDto)
                .block();

        if (stock.getSymbol() == null) {
            throw new StockNotFoundException("Stock not found for ticker: " + ticker);
        }

        return stock;
    }

    //todo map StockSearch to Dto, handle no search results
    public List<Stock.StockSearch> findAllStocksByKeyword(String keyword) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "SYMBOL_SEARCH")
                        .queryParam("keywords", keyword)
                        .queryParam("apikey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(AlphaVantageResponse.class)
                .map(response -> response.getStocks())
                .defaultIfEmpty(Collections.emptyList())
                .block();
    }

}
