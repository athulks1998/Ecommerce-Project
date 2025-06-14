package com.task.product.dto;

/**
 * @author Athul KS
 * Data Transfer Object  for product requests and responses.
 */
public record ProductDTO(
    String id,
    String name,
    String description,
    double price,
    int stock
) {}