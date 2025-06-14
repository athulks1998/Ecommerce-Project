package com.task.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.task.user.model.User;

/**
 * @author Athul K S
 * Mongo Repository for User operations
 * 
 */
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}