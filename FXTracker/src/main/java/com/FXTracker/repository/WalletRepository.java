package com.FXTracker.repository;

import com.FXTracker.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * MongoDB Repository Interface for handling operations on Wallets DB.
 */
@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {
    Optional<Wallet> findByUserId(String id);
    boolean existsByUserId(String userId);
}
