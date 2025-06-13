package com.task.user.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    // Use a secure random key of at least 32 characters for HS256
    private static final String SECRET_KEY = "my-super-secret-key-which-is-very-long-123456";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Generates a JWT token for the given username.
     * The token is valid for 1 hour from the time of creation.
     *
     * @param username the username for which the token is generated
     * @return a signed JWT token as a String
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 )) // 1 hour
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}