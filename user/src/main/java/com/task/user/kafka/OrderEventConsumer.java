package com.task.user.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.task.common.constants.EventType;
import com.task.common.events.OrderEvent;
import com.task.common.events.UserEvent;
import com.task.user.model.Notification;
import com.task.user.model.User;
import com.task.user.repository.UserRepository;

/**
 * Listens for order events from Kafka.
 */
@Service
public class OrderEventConsumer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEventProducer userEventProducer;

    @KafkaListener(topics = "order-events", groupId = "user-service")
    public void handleOrderEvent(OrderEvent event) {
        if (event.getEventType() == EventType.ORDER_PLACED) {
            // Fetch user address
            User user = userRepository.findById(event.getUserId()).orElse(null);
            if (user != null) {
                // Send address back to product service
                UserEvent userEvent = new UserEvent();
                userEvent.setEventType(EventType.USER_ADDRESS);
                userEvent.setOrderId(event.getOrderId());
                userEvent.setUserId(user.getId());
                userEvent.setAddress(user.getAddress()); // Assume address field exists
                userEventProducer.sendUserAddressEvent(userEvent);
            }
        } else if (event.getEventType() == EventType.ORDER_STATUS_UPDATED) {
            // Add notification to user
            User user = userRepository.findById(event.getUserId()).orElse(null);
            if (user != null) {
                Notification notification = new Notification(
                    "Order " + event.getOrderId() + " status updated.",
                    event.getOrderId(),
                    java.time.LocalDate.now().toString()
                );
                if (user.getNotifications() == null) {
                    user.setNotifications(new java.util.ArrayList<>());
                }
                user.getNotifications().add(notification);
                System.out.println(notification);
                userRepository.save(user);
            }
        }
    }
}
