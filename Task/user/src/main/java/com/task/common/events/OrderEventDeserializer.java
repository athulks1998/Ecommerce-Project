package com.task.common.events;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Athul K S
 * Custom Deserializer class for Order Events
 */
public class OrderEventDeserializer implements Deserializer<OrderEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public OrderEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, OrderEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing OrderEvent", e);
        }
    }

    @Override
    public void close() {}
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}
}
