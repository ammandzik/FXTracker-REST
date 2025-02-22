package com.FXTracker.test_container;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class MongoDBTestContainer {

    private static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer(DockerImageName.parse("mongo:7.0.0"))
            .withReuse(true);  // Pozwalamy na ponowne użycie kontenera

    public static MongoDBContainer getInstance() {
        if (!MONGO_DB_CONTAINER.isRunning()) {
            MONGO_DB_CONTAINER.start();
            System.out.println("MongoDB Container started at: " + MONGO_DB_CONTAINER.getReplicaSetUrl());
        }
        System.setProperty("spring.data.mongodb.uri", MONGO_DB_CONTAINER.getReplicaSetUrl());
        return MONGO_DB_CONTAINER;
    }

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        String mongoUri = MONGO_DB_CONTAINER.getReplicaSetUrl();
        System.out.println("Registering MongoDB URI in Spring: " + mongoUri);
        registry.add("spring.data.mongodb.uri", () -> mongoUri);
    }

}