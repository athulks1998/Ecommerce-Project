package com.task.common.events;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Athul K S
 * 
 * Custom Serializer for User events 
 */
public class UserEventSerializer implements Serializer<UserEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, UserEvent data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing UserEvent", e);
        }
    }

    @Override
    public void close() {}
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}
}