
package com.task.common.events;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Athul K S
 * 
 * Custom Deserializer for User events
 */
public class UserEventDeserializer implements Deserializer<UserEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, UserEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing UserEvent", e);
        }
    }

    @Override
    public void close() {}
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}
}
