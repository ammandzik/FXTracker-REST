package com.FXTracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wallets")
public class Wallet {
    @Id
    private String id;
    @Field(name = "user_id")
    private String userId;
    @Field(name = "currency")
    private String currency;
    @Field(name = "balance")
    private BigDecimal balance;
}
