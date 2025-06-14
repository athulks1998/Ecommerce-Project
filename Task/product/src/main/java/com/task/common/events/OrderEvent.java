package com.task.common.events;

import com.task.common.constants.EventType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Athul KS
 * Event for order actions sent via Kafka.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private EventType eventType; 
    private String orderId;
    private String userId;
    private String address;
}