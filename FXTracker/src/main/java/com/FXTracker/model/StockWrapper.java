package com.FXTracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import java.time.LocalDateTime;

@Getter
@Setter
@With
@Entity
public class StockWrapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime lastAccessed;
    private final String symbol;
    private final String name;
    private String currency;
    private String stockExchange;

    public StockWrapper(String symbol, String name){

        this.symbol = symbol;
        this.name = name;

    }


    public StockWrapper(Long id, LocalDateTime lastAccessed, String symbol, String name, String currency, String stockExchange) {
        this.id = id;
        this.lastAccessed = lastAccessed;
        this.symbol = symbol;
        this.name = name;
        this.currency = currency;
        this.stockExchange = stockExchange;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStockExchange() {
        return stockExchange;
    }


}
