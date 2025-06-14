package com.task.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.user.dto.ApiResponse;
import com.task.user.dto.UserRequest;
import com.task.user.dto.UserResponse;
import com.task.user.service.UserService;

/**
 * @author Athul KS
 * Controller for user registration and login.
 */
@RestController
@RequestMapping("/api/users")
public class Login {

    @Autowired
    private UserService userService;

    /**
     * Registers a new user.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@RequestBody UserRequest userRequest) {
        ApiResponse<UserResponse> response = userService.registerUser(userRequest);
        return ResponseEntity.status(response.code()).body(response);
    }

    /**
     * Authenticates a user and returns JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> loginUser(@RequestBody UserRequest userRequest) {
        ApiResponse<UserResponse> response = userService.authenticate(userRequest);
        return ResponseEntity.status(response.code()).body(response);
    }
}
