package com.FXTracker.repository;

import com.FXTracker.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WalletRepository extends MongoRepository<Wallet, String> {

}
