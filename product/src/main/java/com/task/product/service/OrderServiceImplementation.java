package com.task.product.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.common.constants.EventType;
import com.task.common.events.OrderEvent;
import com.task.product.constants.ErrorCodes;
import com.task.product.constants.OrderStatus;
import com.task.product.constants.ResponseStatus;
import com.task.product.dto.ApiResponse;
import com.task.product.dto.OrderRequest;
import com.task.product.dto.OrderResponse;
import com.task.product.kafka.OrderEventProducer;
import com.task.product.model.Order;
import com.task.product.model.OrderItem;
import com.task.product.model.Product;
import com.task.product.repository.OrderRepository;
import com.task.product.repository.ProductRepository;

/**
 * @author Athul K S
 * Implementation of OrderService for managing order business logic,
 * including stock validation and order status management.
 */
@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderEventProducer orderEventProducer;

    /**
     * INFO : Method to place orders
     */
    @Override
    public ApiResponse<OrderResponse> placeOrder(OrderRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;

        for (OrderRequest.OrderProduct op : request.products()) {
            Optional<Product> productOpt = productRepository.findById(op.productId());
            if (productOpt.isEmpty()) {
                return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.PRD_NOT_FOUND.code, 404);
            }
            Product product = productOpt.get();
            if (product.getStock() < op.quantity()) {
                return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.PRD_OUT_OF_STOCK.code, 400);
            }
            // Reduce stock
            product.setStock(product.getStock() - op.quantity());
            productRepository.save(product);

            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setQuantity(op.quantity());
            item.setPrice(product.getPrice());
            orderItems.add(item);

            total += product.getPrice() * op.quantity();
        }

        Order order = new Order();
        order.setUserId(request.userId());
        order.setItems(orderItems);
        order.setTotalPrice(total);
        order.setOrderDate(LocalDate.now());
        order.setEstimatedDeliveryDate(LocalDate.now().plusDays(5));
        order.setStatus(OrderStatus.PENDING);

        order = orderRepository.save(order);

        //Publish event to request address
        OrderEvent event = new OrderEvent();
        event.setEventType(EventType.ORDER_PLACED);
        event.setOrderId(order.getId());
        event.setUserId(order.getUserId());
        orderEventProducer.sendOrderPlacedEvent(event);

        //Return response 
        return new ApiResponse<>(toOrderResponse(order), ResponseStatus.SUCCESS.value, null, 201);
    }

    /**
     * INFO : Method to get order details by order ID
     */
    @Override
    public ApiResponse<OrderResponse> getOrderById(String orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            return new ApiResponse<>(toOrderResponse(orderOpt.get()), ResponseStatus.SUCCESS.value, null, 200);
        } else {
            return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.ORDER_NOT_FOUND.code, 404);
        }
    }

    /**
     * INFO : Method to cancel an order if it hasn't been shipped or delivered
     */
    @Override
    public ApiResponse<Void> cancelOrder(String orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.ORDER_NOT_FOUND.code, 404);
        }
        Order order = orderOpt.get();
        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
            return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.ORDER_CANNOT_CANCEL.code, 400);
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return new ApiResponse<>(null, ResponseStatus.SUCCESS.value, null, 204);
    }

    /**
     * INFO : Method to get all orders placed by a specific user
     */
    @Override
    public ApiResponse<List<OrderResponse>> getOrdersByUser(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponse> responses = orders.stream().map(this::toOrderResponse).toList();
        return new ApiResponse<>(responses, ResponseStatus.SUCCESS.value, null, 200);
    }

    /**
     * INFO : Method for admin to update the status of an order
     */
    @Override
    public ApiResponse<OrderResponse> updateOrderStatus(String orderId, String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.ORDER_NOT_FOUND.code, 404);
        }
        Order order = orderOpt.get();
        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
            orderRepository.save(order);

            // Send event to user service
            OrderEvent event = new OrderEvent();
            event.setEventType(EventType.ORDER_STATUS_UPDATED);
            event.setOrderId(order.getId());
            event.setUserId(order.getUserId());
            event.setAddress(order.getAddress());
            orderEventProducer.sendOrderPlacedEvent(event);

            return new ApiResponse<>(toOrderResponse(order), ResponseStatus.SUCCESS.value, null, 200);
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.ORDER_INVALID_STATUS.code, 400);
        }
    }

    /**
     * INFO : Helper method to convert Order entity to OrderResponse DTO
     */
    private OrderResponse toOrderResponse(Order order) {
        List<OrderResponse.OrderItemDTO> items = order.getItems().stream()
                .map(i -> new OrderResponse.OrderItemDTO(i.getProductId(), i.getProductName(), i.getQuantity(), i.getPrice()))
                .toList();
        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                items,
                order.getTotalPrice(),
                order.getOrderDate(),
                order.getEstimatedDeliveryDate(),
                order.getStatus(),
                order.getAddress()
        );
    }
}