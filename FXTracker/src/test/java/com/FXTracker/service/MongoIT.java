package com.FXTracker.service;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class MongoIT {
    @DisplayName("given object to save"
            + " when save object using MongoDB template"
            + " then object is saved")
    @Test
    public void test(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

        // when
        mongoTemplate.save(objectToSave, "collection");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
                .containsOnly("value");

        long count = mongoTemplate.getCollection("collection").countDocuments();

        assertEquals(1, count, "Flapdoodle MongoDB should be working.");
    }

    @Test
    void testEmbeddedMongo(@Autowired MongoTemplate mongoTemplate) {

        mongoTemplate.getCollection("testCollection");

        long count = mongoTemplate.getCollection("testCollection").countDocuments();
        assertEquals(1, count, "Flapdoodle MongoDB should be working.");
    }
}