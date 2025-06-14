package com.task.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.user.dto.ApiResponse;
import com.task.user.dto.UserRequest;
import com.task.user.dto.UserResponse;
import com.task.user.model.User;
import com.task.user.repository.UserRepository;
import com.task.user.util.JwtUtil;

/**
 * @author Athul KS
 * Handles user registration and authentication logic.
 */
@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Registers a new user and saves to database.
     */
    @Override
    public ApiResponse<UserResponse> registerUser(UserRequest request) {
        try {
            User user = toUser(request);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User registeredUser = userRepository.save(user);
            return new ApiResponse<>(toUserResponse(registeredUser, "success", null, 200, null), "success", null, 200);
        } catch (Exception e) {
            return new ApiResponse<>(null, "fail", "USR_REG_ERR", 500);
        }
    }

    /**
     * Authenticates a user and returns JWT if valid.
     */
    @Override
    public ApiResponse<UserResponse> authenticate(UserRequest request) {
        try {
            User user = userRepository.findByUsername(request.username());
            if (user == null) {
                return new ApiResponse<>(null, "fail", "USR_AUTH_FAIL", 401);
            }
            if (passwordEncoder.matches(request.password(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                return new ApiResponse<>(toUserResponse(user, "success", null, 200, token), "success", null, 200);
            } else {
                return new ApiResponse<>(null, "fail", "USR_AUTH_FAIL", 401);
            }
        } catch (Exception e) {
            return new ApiResponse<>(null, "fail", "USR_AUTH_ERR", 500);
        }
    }

    /**
     * Maps UserRequest to User entity.
     */
    private User toUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setNotifications(request.notifications());
        user.setAddress(request.address()); 
        return user;
    }

    /**
     * Maps User entity to UserResponse.
     */
    private UserResponse toUserResponse(User user, String status, String errorCode, int code, String token) {
        return new UserResponse(
            user != null ? user.getId() : null,
            user != null ? user.getUsername() : null,
            user != null ? user.getEmail() : null,
            user != null ? user.getFirstName() : null,
            user != null ? user.getLastName() : null,
            user != null ? user.getNotifications() : null,
            user != null ? user.getAddress() : null, 
            status,
            errorCode,
            code,
            token
        );
    }
}