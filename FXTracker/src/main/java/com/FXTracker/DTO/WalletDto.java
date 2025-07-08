package com.FXTracker.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents Wallet DTO with details such as id, userId, currency, balance.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {

    private String id;
    @NotNull(message = "User ID cannot be null")
    @NotEmpty(message = "User ID cannot be empty")
    private String userId;
    private String currency;
    private double balance;
}
