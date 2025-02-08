package com.FXTracker.repository;

import com.FXTracker.model.Stock;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface StockRepository extends MongoRepository<Stock, String> {

    @ExistsQuery("{ 'stock.symbol' : ?0 }")
    boolean existsBySymbol(String symbol);

    @Query("{ 'symbol' : symbol }")
    Optional<Stock> findStock(String symbol);
}
