package com.FXTracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "portfolios")
public class Portfolio {

    private String id;
    @Id
    @Field(name = "user_id")
    private String userId;
    @Field(name = "stocks")
    private Map<String, String> stocks;
    @Field(name = "balance")
    private Float balance;
    @Field(name = "profit")
    private Float profit;
    @Field(name = "loss")
    private Float loss;
}
