package com.FXTracker.repository;

import com.FXTracker.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * MongoDB Repository Interface for handling operations on Stocks DB.
 */
@Repository
public interface StockRepository extends MongoRepository<Stock, String> {
    boolean existsBySymbol(String symbol);
    Optional<Stock> findStockBySymbol(String symbol);

}
