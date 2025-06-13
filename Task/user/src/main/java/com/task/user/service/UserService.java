package com.task.user.service;

import com.task.user.dto.UserRequest;
import com.task.user.dto.UserResponse;

/**
 * @author Athul KS
 * Service interface for user operations.
 */
public interface UserService {

    /**
     * Registers a new user.
     * @param request user registration data
     * @return user response
     */
    UserResponse registerUser(UserRequest request);

    /**
     * Authenticates a user and returns a JWT if successful.
     * @param request user login data
     * @return user response
     */
    UserResponse authenticate(UserRequest request);
}