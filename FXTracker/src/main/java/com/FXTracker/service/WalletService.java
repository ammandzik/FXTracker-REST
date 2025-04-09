package com.FXTracker.service;

import com.FXTracker.DTO.WalletDto;
import com.FXTracker.exception.InsufficientFundsException;
import com.FXTracker.exception.ResourceNotFoundException;
import com.FXTracker.exception.WalletServiceException;
import com.FXTracker.mapper.WalletMapper;
import com.FXTracker.model.Wallet;
import com.FXTracker.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.FXTracker.advice.ExceptionMessages.OPERATION_NOT_ALLOWED;

/**
 * Service class for handling operations on wallets.
 * Handles operations like creating, getting by user ID, updating, adding funds, finding all of existing wallets.
 */
@Service
@RequiredArgsConstructor
@Log4j2
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

        log.info("Invoked createWallet method");

        if (walletDto == null) {
            log.warn("Error while saving wallet - wallet is null");
            throw new WalletServiceException(OPERATION_NOT_ALLOWED.getDescription());
        }
        var entity = walletMapper.toEntity(walletDto);
        log.info("Saving created wallet for user with ID {} to DB", walletDto.getUserId());
        walletRepository.save(entity);
        return entity;

    }

    /**
     * @param userId takes user id as a parameter
     * @param amount represents amount, which should be added/taken from the wallet
     * @return sum consisting of amount and initial balance
     */

    //todo
    public double updateWalletBalance(String userId, double amount) {

        var wallet = walletRepository.findByUserId(userId)
                        .orElseThrow(()-> new ResourceNotFoundException(String.format("No portfolio found for user with ID: %s",userId)));
        log.info("Invoked manageFundsBalance method");
        if (wallet == null) {
            log.warn("Wallet is null value.");
            throw new WalletServiceException(OPERATION_NOT_ALLOWED.getDescription());

        }
        double sum = wallet.getBalance() + (amount * -1);
        if (sum < 0)
            throw new InsufficientFundsException("Operation not allowed - insufficient funds.");
        else wallet.setBalance(sum);

        log.info("Returning wallet balance sum");
        return sum;
    }
}
