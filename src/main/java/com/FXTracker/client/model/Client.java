package com.FXTracker.client.model;

import com.FXTracker.wallet.model.Wallet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {

    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private Wallet wallet;
}
