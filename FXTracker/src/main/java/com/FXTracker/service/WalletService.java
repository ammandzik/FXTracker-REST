package com.FXTracker.service;

import com.FXTracker.DTO.WalletDto;
import com.FXTracker.exception.InsufficientFundsException;
import com.FXTracker.exception.WalletServiceException;
import com.FXTracker.mapper.WalletMapper;
import com.FXTracker.model.Wallet;
import com.FXTracker.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for handling operations on wallets.
 * Handles operations like creating, getting by user ID, updating, adding funds, finding all of existing wallets.
 */
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    /**
     * handles creating new wallet
     *
     * @param walletDto takes object of class WalletDto as a parameter
     * @return Wallet entity
     */
    public Wallet createWallet(WalletDto walletDto) {

        try {
            var entity = walletMapper.toEntity(walletDto);
            walletRepository.save(entity);
            return entity;

        } catch (NullPointerException npe) {
            throw new WalletServiceException("Error while creating a wallet occurred.");
        }
    }

    /**
     * @param walletDto takes object of class WalletDto as a parameter
     * @param amount    represents amount, which should be added/taken from the wallet
     * @return sum of amount and initial balance
     */
    public float manageFundsBalance(WalletDto walletDto, float amount) {

        try {
            float initBalance = walletDto.getBalance();
            float sum = initBalance + amount;
            if (sum < 0) {
                throw new InsufficientFundsException("Operation not allowed - insufficient funds.");
            }
            walletDto.setBalance(initBalance + amount);

            return sum;

        } catch (NullPointerException npe) {
            throw new WalletServiceException("Error while managing funds in the wallet.");
        }
    }
}
