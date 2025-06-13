package com.task.product.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.task.product.model.Order;

/**
 * @author Athul KS
 * Repository interface for Order entity, providing CRUD operations and user order queries.
 */
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
}