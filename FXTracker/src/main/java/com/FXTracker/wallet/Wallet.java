package com.FXTracker.wallet;

import com.FXTracker.user.User;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Wallet {

    private Long id;
    private User user;
    private Currency currency;
    private Double balance;


}
