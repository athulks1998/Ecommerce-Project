package com.task.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserResponse registerUser(UserRequest request) {
        try {
            User user = toUser(request);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User registeredUser = userRepository.save(user);
            return toUserResponse(registeredUser, "success", null, 200, null);
        } catch (Exception e) {
            return new UserResponse(null, null, null, null, null, null, "fail", "USR_REG_ERR", 500, null);
        }
    }

    /**
     * Authenticates a user and returns JWT if valid.
     */
    @Override
    public UserResponse authenticate(UserRequest request) {
        try {
            User user = userRepository.findByUsername(request.username());
            if (user != null && passwordEncoder.matches(request.password(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                return toUserResponse(user, "success", null, 200, token);
            } else {
                return new UserResponse(null, null, null, null, null, null, "fail", "USR_AUTH_FAIL", 401, null);
            }
        } catch (Exception e) {
            return new UserResponse(null, null, null, null, null, null, "fail", "USR_AUTH_ERR", 500, null);
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
            status,
            errorCode,
            code,
            token
        );
    }
}