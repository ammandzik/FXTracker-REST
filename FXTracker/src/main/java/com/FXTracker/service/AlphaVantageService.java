package com.FXTracker.service;

import com.FXTracker.DTO.StockDto;
import com.FXTracker.exception.StockNotFoundException;
import com.FXTracker.exception.StockServiceException;
import com.FXTracker.mapper.StockMapper;
import com.FXTracker.model.Stock;
import com.FXTracker.response.AlphaVantageResponse;
import com.FXTracker.response.Function;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

/**
 * Service class for fetching stocks from Alpha Vantage API.
 * Handles operations like getting single stock data, finding stocks matching keyword.
 */
@Slf4j
@Service
public class AlphaVantageService {

    private final String API_KEY;
    private final WebClient webClient;
    private final StockMapper stockMapper;
    private final StockMapper.StockSearchMapper stockSearchMapper;
    private final Logger logger = LoggerFactory.getLogger(AlphaVantageService.class);

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
    public StockDto getSingleStockDataFromAPI(String ticker) {

        logger.info("Received request to get stock for ticker: {}", ticker);

        try {
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
                    .map(stockMapper::toDto)
                    .block();

            if (stock.getSymbol() == null) {
                logger.warn("No stock was found with ticker: {}", ticker);
                throw new StockNotFoundException(String.format("Stock not found for ticker: %s ", ticker));
            } else {
                logger.info("Returning {} stock for ticker: {}", stock, ticker);
                return stock;
            }

        } catch (NullPointerException exception) {
            throw new StockServiceException("Error while fetching stocks.");
        }
    }

    /**
     * @param keyword represents keyword used to find partially matching stocks names
     * @return list of objects of nested class StockSearchDto
     */
    public List<StockDto.StockSearchDto> findAllStocksByKeywordInAPI(String keyword) {

        logger.info("Received request to search stocks with keyword: {}", keyword);

        try {
            List<Stock.StockSearch> stocks = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/query")
                            .queryParam("function", Function.SYMBOL_SEARCH)
                            .queryParam("keywords", keyword)
                            .queryParam("apikey", API_KEY)
                            .build())
                    .retrieve()
                    .bodyToMono(AlphaVantageResponse.class)
                    .map(AlphaVantageResponse::stocks)
                    .defaultIfEmpty(Collections.emptyList())
                    .block();

            if (stocks.isEmpty()) {
                logger.warn("No stocks were found with keyword: {}", keyword);
                throw new StockNotFoundException(String.format("No stocks were found for keyword: %s", keyword));

            } else {
                logger.info("Returning {} stocks for keyword: {}", stocks, keyword);
                return stocks.stream()
                        .map(stockSearchMapper::toDto)
                        .toList();
            }

        } catch (NullPointerException exception) {
            throw new StockServiceException("Error while fetching stocks.");
        }
    }
}

