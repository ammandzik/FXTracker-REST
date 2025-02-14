package com.FXTracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * Represents User Portfolio with details such as id, user id, stocks, balance, profit, and loss.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "portfolios")
public class Portfolio {

    @Id
    private String id;
    @Field(name = "user_id")
    private String userId;
    @Field(name = "stocks")
    private Map<String, String> stocks;
    @Field(name = "balance")
    private Double balance;
    @Field(name = "funds_spent")
    private Double fundsSpent;
    @Field(name = "profit")
    private Double profit;
    @Field(name = "loss")
    private Double loss;
}
