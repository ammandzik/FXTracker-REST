package com.FXTracker.repository;

import com.FXTracker.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StockRepository extends MongoRepository<Stock, String> {

    boolean existsBySymbol(String symbol);

    Optional<Stock> findStockBySymbol(String symbol);
}
