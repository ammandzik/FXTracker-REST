package com.FXTracker.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@TestConfiguration
public class EmbeddedMongoConfig {
    @Bean
    public final MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27018/test-db");
    }
    @Bean
    public final MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "test-db");
    }
}
