package com.FXTracker.repository;

import com.FXTracker.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoDB Repository Interface for handling operations on Users DB.
 */
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUserId(String userId);
}
