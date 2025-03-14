package com.FXTracker.configuration;

import com.FXTracker.model.Stock;
import com.FXTracker.serialization.StockDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JacksonConfig {
    @Bean
    public static ObjectMapper getObjectMapper() {

        var objectMapper = new ObjectMapper();
        var module = new SimpleModule();

        module.addKeyDeserializer(Stock.class, new StockDeserializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
