package com.FXTracker.test_container;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MongoDBTestContainer {
    private static final String IMAGE_VERSION = "mongo:7.0.0";
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.0");

    public static MongoDBContainer getInstance() {
        if (mongoDBContainer == null) {
            mongoDBContainer = new MongoDBContainer(IMAGE_VERSION);
            mongoDBContainer.start();
        }
        return mongoDBContainer;
    }

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

}
