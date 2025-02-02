package com.FXTracker.deserializer;

import com.FXTracker.model.Stock;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

public class StockDeserializer extends KeyDeserializer {

    @Override
    public Stock deserializeKey(String key, DeserializationContext context) {

        return new Stock(key);
    }
}
