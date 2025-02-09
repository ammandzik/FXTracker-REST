package com.FXTracker.mapper;

import com.FXTracker.DTO.WalletDto;
import com.FXTracker.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public WalletDto toDto(Wallet wallet){

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
