package com.task.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.product.dto.ApiResponse;
import com.task.product.dto.OrderRequest;
import com.task.product.dto.OrderResponse;
import com.task.product.service.OrderService;

/**
 * @author Athul KS
 * Controller for handling order-related endpoints such as placing orders,
 * retrieving order details, cancelling orders, listing user orders, and updating order status.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Places a new order.
     *
     * @param request the order request data
     * @return ApiResponse containing order confirmation or error information
     */
    @PostMapping("/place")
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(@RequestBody OrderRequest request) {
        ApiResponse<OrderResponse> response = orderService.placeOrder(request);
        return ResponseEntity.status(response.code()).body(response);
    }

    /**
     * Retrieves order details by order ID.
     *
     * @param orderId the ID of the order to retrieve
     * @return ApiResponse containing order details or error information
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable String orderId) {
        ApiResponse<OrderResponse> response = orderService.getOrderById(orderId);
        return ResponseEntity.status(response.code()).body(response);
    }

    /**
     * Cancels an order by order ID.
     *
     * @param orderId the ID of the order to cancel
     * @return ApiResponse indicating success or error information
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable String orderId) {
        ApiResponse<Void> response = orderService.cancelOrder(orderId);
        return ResponseEntity.status(response.code()).body(response);
    }

    /**
     * Lists all orders placed by a specific user.
     *
     * @param userId the ID of the user
     * @return ApiResponse containing a list of orders or error information
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getUserOrders(@PathVariable String userId) {
        ApiResponse<List<OrderResponse>> response = orderService.getOrdersByUser(userId);
        return ResponseEntity.status(response.code()).body(response);
    }

    /**
     * Updates the status of an order (admin only).
     *
     * @param orderId the ID of the order
     * @param status the new status
     * @return ApiResponse containing updated order details or error information
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam String status) {
        ApiResponse<OrderResponse> response = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.status(response.code()).body(response);
    }
}