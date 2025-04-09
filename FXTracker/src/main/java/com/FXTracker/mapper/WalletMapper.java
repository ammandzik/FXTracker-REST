package com.FXTracker.mapper;

import com.FXTracker.DTO.WalletDto;
import com.FXTracker.model.Wallet;
import org.springframework.stereotype.Component;

/**
 * Custom Mapper class for Wallet and WalletDto classes.
 */
@Component
public class WalletMapper {

    public static WalletDto toDto(Wallet wallet){

        return WalletDto.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .currency(wallet.getCurrency())
                .userId(wallet.getUserId())
                .build();
    }

    public Wallet toEntity(WalletDto walletDto){

        return Wallet.builder()
                .id(walletDto.getId())
                .balance(walletDto.getBalance())
                .currency(walletDto.getCurrency())
                .userId(walletDto.getUserId())
                .build();
    }
}
