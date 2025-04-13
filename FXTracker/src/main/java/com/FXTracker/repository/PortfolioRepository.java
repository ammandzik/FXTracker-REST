package com.FXTracker.repository;

import com.FXTracker.model.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * MongoDB Repository Interface for handling operations on Portfolios DB.
 */
@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
    Optional<Portfolio> findByUserId(String id);
    boolean existsByUserId(String userId);

}
