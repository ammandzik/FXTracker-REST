package com.FXTracker.service;

import com.FXTracker.DTO.WalletDto;
import com.FXTracker.exception.ExistingResourceException;
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
        if(walletExists(walletDto.getUserId())){
            log.warn("Wallet with user Id {} already exists", walletDto.getUserId());
            throw new ExistingResourceException(String.format("Wallet with user ID: %s already exists!", walletDto.getUserId()));
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

    public Wallet updateWalletBalance(String userId, double amount) {

        log.info("Invoked updateWalletBalance method");

        var wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No wallet was found for user with ID: %s", userId)));

        if (userId == null) {
            log.warn("userId is null!");
            throw new WalletServiceException(OPERATION_NOT_ALLOWED.getDescription());
        }
        double sum = wallet.getBalance() + (amount * -1);
        if (sum < 0)
            throw new InsufficientFundsException("Operation could not be finished due to insufficient funds.");
        else {
            log.info("Updating balance in wallet");
            wallet.setBalance(sum);
        }

        log.info("Saving updated wallet to DB");
        return walletRepository.save(wallet);
    }

    public boolean walletExists(String userId) {

        log.info("Invoked walletExists method");
        return walletRepository.existsByUserId(userId);
    }
}
