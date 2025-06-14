package com.task.common.events;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Athul K S
 * Custom Serializer for Order Event
 */
public class OrderEventSerializer implements Serializer<OrderEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, OrderEvent data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing OrderEvent", e);
        }
    }

    @Override
    public void close() {}
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}
}
