package com.FXTracker.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder builder, @Value("${alphavantage.api.url}") String apiUrl) {
        return builder.baseUrl(apiUrl).build();
    }

}
