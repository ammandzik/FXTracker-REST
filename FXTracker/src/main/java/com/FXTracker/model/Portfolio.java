package com.FXTracker.model;

import com.FXTracker.deserializer.StockDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.TreeMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @JsonDeserialize(keyUsing = StockDeserializer.class)
    private TreeMap<Stock, Long> stocks;
    private BigDecimal balance;
    private BigDecimal profit;
    private BigDecimal loss;

}
