package com.FXTracker.DTO;

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
    private String userId;
    private String currency;
    private Float balance;
}
