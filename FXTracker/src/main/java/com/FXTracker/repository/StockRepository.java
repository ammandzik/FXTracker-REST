package com.FXTracker.repository;

import com.FXTracker.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Map;
import java.util.Optional;

public interface StockRepository extends MongoRepository<Stock, String> {

    @Query("{ 'symbol' : ?0 }")
    boolean existsBySymbol(String symbol);

    @Query("{ 'symbol' : symbol }")
    Optional<Stock> findStock(String symbol);
}
