package com.task.product.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.task.common.events.OrderEvent;

/**
 * Publishes order events to Kafka.
 */
@Service
public class OrderEventProducer {
    
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderPlacedEvent(OrderEvent event) {
        
        kafkaTemplate.send("order-events", event);
    }
}
