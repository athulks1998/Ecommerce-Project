package com.task.product.dto;

/**
 * Generic API response wrapper for all endpoints, containing data, status, error code, and HTTP code.
 */
public record ApiResponse<T>(
    T data,
    String status,
    String errorCode,
    int code
) {}