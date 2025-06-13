package com.task.product.service;

import java.util.List;

import com.task.product.dto.ApiResponse;
import com.task.product.dto.ProductDTO;

/**
 * @author Athul KS
 * Service interface for product operations such as adding, retrieving,
 * listing, and deleting products.
 */
public interface ProductService {

    /**
     * Adds a new product.
     */
    ApiResponse<ProductDTO> addProduct(ProductDTO dto);

    /**
     * Retrieves all products.
     */
    ApiResponse<List<ProductDTO>> getAllProducts();

    /**
     * Retrieves a product by its ID.
     */
    ApiResponse<ProductDTO> getProductById(String id);

    /**
     * Deletes a product by its ID.
     */
    ApiResponse<Void> deleteProduct(String id);
}