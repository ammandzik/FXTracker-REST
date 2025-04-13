package com.FXTracker.repository;

import com.FXTracker.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB Repository Interface for handling operations on Users DB.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
}
