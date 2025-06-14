package com.task.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Athul KS
 * Entity representing an item within an order.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    
    private String productId;
    private String productName;
    private int quantity;
    private double price;
}