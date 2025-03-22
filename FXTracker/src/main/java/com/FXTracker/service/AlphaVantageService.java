package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.exception.StockServiceException;
import com.FXTracker.mapper.StockMapper;
import com.FXTracker.response.AlphaVantageResponse;
import com.FXTracker.response.Function;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.FXTracker.advice.ExceptionMessages.OPERATION_NOT_ALLOWED;
import static java.util.stream.Collectors.toList;

/**
 * Service class for fetching stocks from Alpha Vantage API.
 * Handles operations like getting single stock data, finding stocks matching keyword.
 */
@Service
@Log4j2
public class AlphaVantageService {

    private final String API_KEY;
    private final WebClient webClient;
    private final StockMapper stockMapper;
    private final StockMapper.StockSearchMapper stockSearchMapper;

    public AlphaVantageService(@Value("${alphavantage.api.key}") String apiKey, WebClient webClient, StockMapper stockMapper, StockMapper.StockSearchMapper stockSearchMapper) {
        this.API_KEY = apiKey;
        this.webClient = webClient;
        this.stockMapper = stockMapper;
        this.stockSearchMapper = stockSearchMapper;
    }

    /**
     * @param ticker represents Stock symbol
     * @return object of class StockDto mapped from fetched API Stock
     */
    public Mono<StockDto> getSingleStockDataFromAPI(String ticker) {

        log.info("Received request to get stock for ticker: {}", ticker);

            var stock = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/query")
                            .queryParam("function", Function.GLOBAL_QUOTE)
                            .queryParam("symbol", ticker)
                            .queryParam("apikey", API_KEY)
                            .build())
                    .retrieve()
                    .bodyToMono(AlphaVantageResponse.class)
                    .map(AlphaVantageResponse::stock)
                    .map(stockMapper::toDto);

            if (stock == null) {
                log.warn("No stock was found with ticker: {}", ticker);
                throw new StockNotFoundException(String.format("Stock not found for ticker: %s ", ticker));
            } else {
                log.info("Returning {} stock for ticker: {}", stock, ticker);
                return stock;
            }

    }

    /**
     * @param keyword represents keyword used to find partially matching stocks names
     * @return list of objects of nested class StockSearchDto
     */
    public Mono<List<StockDto.StockSearchDto>> findAllStocksByKeywordInAPI(String keyword) {

        log.info("Received request to search stocks with keyword: {}", keyword);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", Function.SYMBOL_SEARCH)
                        .queryParam("keywords", keyword)
                        .queryParam("apikey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(AlphaVantageResponse.class)
                .map(AlphaVantageResponse::stocks)
                .flatMap(stockList -> {
                    if (stockList == null || stockList.isEmpty()) {
                        return Mono.error(new StockNotFoundException(
                                String.format("No stocks were found for keyword: %s", keyword)));
                    }
                    return Mono.just(stockList.stream()
                            .map(stockSearchMapper::toDto)
                            .collect(toList()));
                })
                .doOnSuccess(s -> log.info("Returning {} stocks for keyword: {}", s.size(), keyword))
                .onErrorResume(e -> {
                    log.warn("Error occurred while fetching stocks: {}", e.getMessage());
                    return Mono.error(new StockServiceException(OPERATION_NOT_ALLOWED.name()));
                });
    }
}

