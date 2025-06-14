package com.task.user.dto;

import java.util.List;

import com.task.user.model.Notification;

/**
 * @author Athul KS
 * DTO for user registration and login requests.
 */
public record UserRequest(
    String username,
    String password,
    String email,
    String firstName,
    String lastName,
    List<Notification> notifications,
    String address
) {}