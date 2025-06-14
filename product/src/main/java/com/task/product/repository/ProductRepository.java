package com.task.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.task.product.model.Product;

/**
 * @author Athul KS
 * Repository interface for Product entity, providing CRUD operations.
 */
public interface ProductRepository extends MongoRepository<Product, String> {
}