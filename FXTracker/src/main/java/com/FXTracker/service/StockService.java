package com.FXTracker.service;

import com.FXTracker.alpha_vantage.AlphaVantageResponse;
import com.FXTracker.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class StockService {

    private static final String API_KEY = "IZA7PDJIYSW0RL7V";

    private WebClient webClient;

    public StockService(WebClient webClient) {
        this.webClient = webClient;
    }


    // todo throw stock not found exc in case of null
    public Stock getSingleStockData(String ticker) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "GLOBAL_QUOTE")
                        .queryParam("symbol", ticker)
                        .queryParam("apikey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(AlphaVantageResponse.class)
                .map(AlphaVantageResponse::getStock)
                .block();
    }

    //todo string to wrapped list of stocks
    public String findAllStocksByKeyword(String keyword) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "SYMBOL_SEARCH")
                        .queryParam("keywords", keyword)
                        .queryParam("apikey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
