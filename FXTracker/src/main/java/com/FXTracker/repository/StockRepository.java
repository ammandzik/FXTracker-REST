package com.FXTracker.repository;

import com.FXTracker.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StockRepository extends MongoRepository<Stock, String> {

    Optional<Stock> findStock(String symbol);

    boolean stockExistsInDataBase(String symbol);
}
