package com.task.product.dto;

import java.time.LocalDate;
import java.util.List;

import com.task.product.constants.OrderStatus;

/**
 * @author Athul KS
 * DTO for order responses, including order details and status.
 */
public record OrderResponse(
    String orderId,
    String userId,
    List<OrderItemDTO> items,
    double totalPrice,
    LocalDate orderDate,
    LocalDate estimatedDeliveryDate,
    OrderStatus status,
    String address
) {
    /**
     * DTO for individual order item details in the response.
     */
    public record OrderItemDTO(String productId, String productName, int quantity, double price) {}
}