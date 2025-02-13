package com.FXTracker.serialization;

import com.FXTracker.model.Stock;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

/**
 * Stock deserializer class, that handles deserialization from Json fields to Stock class fields
 */
public class StockDeserializer extends KeyDeserializer {
    @Override
    public Stock deserializeKey(String key, DeserializationContext context) {

        return new Stock();
    }
}
