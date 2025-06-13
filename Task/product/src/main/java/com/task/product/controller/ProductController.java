package com.task.product.controller;

/**
 * @author Athul KS
 * Controller for handling product-related endpoints such as adding,
 * retrieving, listing, and deleting products.
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.product.dto.ApiResponse;
import com.task.product.dto.ProductDTO;
import com.task.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Adds a new product.
     *
     * @param productDTO the product data to add
     * @return ApiResponse containing the added product or error information
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<ProductDTO>> addProduct(@RequestBody ProductDTO productDTO) {
        ApiResponse<ProductDTO> response = productService.addProduct(productDTO);
        return ResponseEntity.status(response.code()).body(response);
    }

    /**
     * Retrieves all products.
     *
     * @return ApiResponse containing a list of all products or error information
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        ApiResponse<List<ProductDTO>> response = productService.getAllProducts();
        return ResponseEntity.status(response.code()).body(response);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return ApiResponse containing the product or error information
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable String id) {
        ApiResponse<ProductDTO> response = productService.getProductById(id);
        return ResponseEntity.status(response.code()).body(response);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @return ApiResponse indicating success or error information
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {
        ApiResponse<Void> response = productService.deleteProduct(id);
        return ResponseEntity.status(response.code()).body(response);
    }
}