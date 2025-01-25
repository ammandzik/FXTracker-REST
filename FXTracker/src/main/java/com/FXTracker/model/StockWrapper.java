package com.FXTracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import yahoofinance.Stock;

import java.time.LocalDateTime;

@Getter
@With
@AllArgsConstructor
@Entity
public class StockWrapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final Stock stock;
    private final LocalDateTime lastAccessed;

    public StockWrapper(final Stock stock) {

        this.stock = stock;
        this.lastAccessed = LocalDateTime.now();
    }

}
