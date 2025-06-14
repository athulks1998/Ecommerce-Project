package com.task.user.dto;

import java.util.List;

import com.task.user.model.Notification;

/**
 * @author Athul KS
 * DTO for user responses.
 */
public record UserResponse(
    String id,
    String username,
    String email,
    String firstName,
    String lastName,
    List<Notification> notifications,
    String address,
    String status,
    String errorCode,
    int code,
    String token
) {}