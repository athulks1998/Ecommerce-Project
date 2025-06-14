package com.task.user.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.task.common.events.UserEvent;

/**
 * @author Athul K S
 * Publishes user events to Kafka.
 */
@Service
public class UserEventProducer {
    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void sendUserAddressEvent(UserEvent event) {
        try {
            System.out.println("Sending UserEvent: " + event);
            kafkaTemplate.send("user-events", event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
