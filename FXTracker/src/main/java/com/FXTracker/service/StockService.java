package com.FXTracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class StockService {
    private static final String API_KEY = "IZA7PDJIYSW0RL7V";
    @Autowired
    private WebClient webClient;

    //todo string to stock class
    public String getSingleStockData(String ticker) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "GLOBAL_QUOTE")
                        .queryParam("symbol", ticker)
                        .queryParam("apikey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //                .bodyToMono(AlphaVantageResponse.class)
        //                .map(AlphaVantageResponse::getGlobalQuote)
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
