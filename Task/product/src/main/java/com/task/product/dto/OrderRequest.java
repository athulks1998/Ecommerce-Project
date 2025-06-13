package com.task.product.dto;

import java.util.List;

/**
 * @author Athul KS
 * DTO for order placement requests, containing user ID and product list.
 */
public record OrderRequest(
    String userId,
    List<OrderProduct> products
) {
    /**
     * Nested record representing a product in the order request.
     *
     * @param productId The ID of the product.
     * @param quantity  The quantity of the product ordered.
     */
    public record OrderProduct(String productId, int quantity) {}
}