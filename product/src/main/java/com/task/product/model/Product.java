package com.task.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Athul KS
 * Entity representing a product in the system.
 */
@Data
@Getter
@Setter
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private int stock; 
}