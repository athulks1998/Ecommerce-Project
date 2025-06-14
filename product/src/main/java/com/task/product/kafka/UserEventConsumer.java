package com.task.product.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.task.common.constants.EventType;
import com.task.common.events.UserEvent;
import com.task.product.model.Order;
import com.task.product.repository.OrderRepository;

/**
 * Listens for user events from Kafka.
 */
@Service
public class UserEventConsumer {

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "user-events", groupId = "product-service")
    public void handleUserEvent(UserEvent event) {
        if (event.getEventType() == EventType.USER_ADDRESS) {
            // Find the order and update its address
            Order order = orderRepository.findById(event.getOrderId()).orElse(null);
            if (order != null) {
                order.setAddress(event.getAddress());
                orderRepository.save(order);
            }
        }
    }
}
