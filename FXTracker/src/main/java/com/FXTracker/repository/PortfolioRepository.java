package com.FXTracker.repository;

import com.FXTracker.model.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {

    Optional<Portfolio> findByUserId(String id);

}
