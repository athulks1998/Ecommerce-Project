package com.task.product.constants;

/**
 * @author Athul KS  
 * Enum containing error codes and messages for product and order operations.
 */
public enum ErrorCodes {
    PRD_ADD_ERR("PRD_ADD_ERR", "Failed to add product"),
    PRD_LIST_ERR("PRD_LIST_ERR", "Failed to list products"),
    PRD_GET_ERR("PRD_GET_ERR", "Failed to get product"),
    PRD_NOT_FOUND("PRD_NOT_FOUND", "Product not found"),
    PRD_DEL_ERR("PRD_DEL_ERR", "Failed to delete product"),
    PRD_OUT_OF_STOCK("PRD_OUT_OF_STOCK", "Product out of stock"),
    ORDER_NOT_FOUND("ORDER_NOT_FOUND", "Order not found"),
    ORDER_CANNOT_CANCEL("ORDER_CANNOT_CANCEL", "Order cannot be cancelled"),
    ORDER_INVALID_STATUS("ORDER_INVALID_STATUS", "Invalid order status");

    public final String code;
    public final String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }
}