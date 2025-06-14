package com.task.common.events;

import com.task.common.constants.EventType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Athul KS
 * Event class for user info sent via Kafka.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {
    private EventType eventType;
    private String orderId;
    private String userId;
    private String address;
}