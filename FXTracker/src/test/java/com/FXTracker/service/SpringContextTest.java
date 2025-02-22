package com.FXTracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testSpringApplicationContextLoads() {
        assertNotNull(applicationContext, "Spring ApplicationContext should be loaded");
        assertTrue(applicationContext.containsBean("mongoTemplate"), "MongoTemplate should be in ApplicationContext");
    }

    @Test
    void testMongoDBContainerStarts() {
        var mongoDBContainer = new MongoDBContainer("mongo:7.0.0");
        mongoDBContainer.start();
        assertTrue(mongoDBContainer.isRunning(), "MongoDB Testcontainer should be running");
    }

    @Test
    void testMongoTemplateIsInjected() {
        assertNotNull(mongoTemplate, "MongoTemplate should not be null");
        assertNotNull(mongoTemplate.getDb(), "MongoTemplate database should not be null");
    }
}

