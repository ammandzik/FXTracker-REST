package com.FXTracker.repository;

import com.FXTracker.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT s FROM Stock s WHERE s.symbol = :symbol")
    Optional<Stock> findStock(String symbol);
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Stock s WHERE s.symbol = :symbol")
    boolean stockExistsInDataBase(String symbol);
}
