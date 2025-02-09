package com.FXTracker.service;

import com.FXTracker.DTO.WalletDto;
import com.FXTracker.mapper.WalletMapper;
import com.FXTracker.model.Wallet;
import com.FXTracker.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    public Wallet createWallet(WalletDto walletDto) {

        var entity = walletMapper.toEntity(walletDto);

        walletRepository.save(entity);

        return entity;
    }
}
