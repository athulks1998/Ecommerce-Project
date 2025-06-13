package com.task.product.service;

import java.util.List;

import com.task.product.dto.ApiResponse;
import com.task.product.dto.OrderRequest;
import com.task.product.dto.OrderResponse;

/**
 * @author Athul KS
 * Service interface for order operations such as placing, retrieving, cancelling,
 * listing, and updating orders.
 */
public interface OrderService {

    /**
     * Places a new order.
     */
    ApiResponse<OrderResponse> placeOrder(OrderRequest request);

    /**
     * Retrieves an order by its ID.
     */
    ApiResponse<OrderResponse> getOrderById(String orderId);

    /**
     * Cancels an order by its ID.
     */
    ApiResponse<Void> cancelOrder(String orderId);

    /**
     * Retrieves all orders placed by a specific user.
     */
    ApiResponse<List<OrderResponse>> getOrdersByUser(String userId);

    /**
     * Updates the status of an order.
     */
    ApiResponse<OrderResponse> updateOrderStatus(String orderId, String status);
}