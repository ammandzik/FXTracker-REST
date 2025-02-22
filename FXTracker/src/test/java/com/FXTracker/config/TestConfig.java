package com.FXTracker.config;

import com.FXTracker.test_container.MongoDBTestContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.testcontainers.containers.MongoDBContainer;

@TestConfiguration
public class TestConfig {

    @Bean
    public MongoDBContainer mongoDBContainer() {
        MongoDBContainer container = MongoDBTestContainer.getInstance();
        System.setProperty("spring.data.mongodb.uri", container.getReplicaSetUrl());
        return container;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        String uri = MongoDBTestContainer.getInstance().getReplicaSetUrl();
        System.out.println("Using MongoDB URI in TestConfig: " + uri);
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(uri));
    }

}
