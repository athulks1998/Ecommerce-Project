package com.task.product.security;

import java.io.IOException;
import java.security.Key;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Athul KS
 * Checks JWT token in requests.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Use the same secret as in user service!
    private static final String SECRET_KEY = "my-super-secret-key-which-is-very-long-123456";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Validates JWT from Authorization header.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("JWT Token: " + token);
        try {
            Jwts.parser().setSigningKey(KEY).build().parseClaimsJws(token);

            // Successful JWT validation
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("user", null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response); // Token valid, continue
        } catch (JwtException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}