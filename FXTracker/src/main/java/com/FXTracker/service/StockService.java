package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.alpha_vantage.AlphaVantageResponse;
import com.FXTracker.alpha_vantage.Function;
import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.exception.StockServiceException;
import com.FXTracker.mapper.StockMapper;
import com.FXTracker.model.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {

    private static final String API_KEY = "IZA7PDJIYSW0RL7V";

    private final WebClient webClient;
    private final StockMapper stockMapper;
    private final StockMapper.StockSearchMapper stockSearchMapper;

    public StockDto getSingleStockData(String ticker) {

        var stock = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", Function.GLOBAL_QUOTE)
                        .queryParam("symbol", ticker)
                        .queryParam("apikey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(AlphaVantageResponse.class)
                .map(AlphaVantageResponse::getStock)
                .map(stockMapper::toDto)
                .block();

        if (stock.getSymbol() == null) {
            throw new StockNotFoundException(String.format("Stock not found for ticker: %s ", ticker));
        }

        return stock;
    }

    public List<StockDto.StockSearchDto> findAllStocksByKeyword(String keyword) {

        List<Stock.StockSearch> stocks = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", Function.SYMBOL_SEARCH)
                        .queryParam("keywords", keyword)
                        .queryParam("apikey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(AlphaVantageResponse.class)
                .map(response -> response.getStocks())
                .defaultIfEmpty(Collections.emptyList())
                .block();

        if (stocks.isEmpty()) {
            throw new StockNotFoundException(String.format("No stocks were found for keyword: %s", keyword));

        } else if (stocks == null) {
            throw new StockServiceException(String.format("Error while fetching stocks."));

        } else {
            return stocks.stream()
                    .map(stockSearchMapper::toDto)
                    .toList();

        }
    }

}
