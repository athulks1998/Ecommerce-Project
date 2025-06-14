package com.task.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.product.constants.ErrorCodes;
import com.task.product.constants.ResponseStatus;
import com.task.product.dto.ApiResponse;
import com.task.product.dto.ProductDTO;
import com.task.product.model.Product;
import com.task.product.repository.ProductRepository;

/**
 * @author Athul KS
 * Implementation of ProductService for managing product business logic,
 * including manual mapping and error handling.
 */
@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Converts a ProductDTO to a Product entity.
     *
     * @param dto the ProductDTO to convert
     * @return the Product entity
     */
    private Product toProduct(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.id());
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        return product;
    }

    /**
     * Converts a Product entity to a ProductDTO.
     *
     * @param product the Product entity to convert
     * @return the ProductDTO
     */
    private ProductDTO toProductDTO(Product product) {
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock()
        );
    }

    /**
     * Adds a new product to the system.
     *
     * @param dto the product data to add
     * @return ApiResponse containing the added product or error information
     */
    @Override
    public ApiResponse<ProductDTO> addProduct(ProductDTO dto) {
        try {
            Product product = toProduct(dto);
            Product saved = productRepository.save(product);
            return new ApiResponse<>(toProductDTO(saved), ResponseStatus.SUCCESS.value, null, 200);
        } catch (Exception e) {
            return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.PRD_ADD_ERR.code, 500);
        }
    }

    /**
     * Retrieves all products from the system.
     *
     * @return ApiResponse containing a list of all products or error information
     */
    @Override
    public ApiResponse<List<ProductDTO>> getAllProducts() {
        try {
            List<ProductDTO> products = productRepository.findAll()
                    .stream()
                    .map(this::toProductDTO)
                    .toList();
            return new ApiResponse<>(products, ResponseStatus.SUCCESS.value, null, 200);
        } catch (Exception e) {
            return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.PRD_LIST_ERR.code, 500);
        }
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return ApiResponse containing the product or error information
     */
    @Override
    public ApiResponse<ProductDTO> getProductById(String id) {
        try {
            Optional<Product> productOpt = productRepository.findById(id);
            if (productOpt.isPresent()) {
                return new ApiResponse<>(toProductDTO(productOpt.get()), ResponseStatus.SUCCESS.value, null, 200);
            } else {
                return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.PRD_NOT_FOUND.code, 404);
            }
        } catch (Exception e) {
            return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.PRD_GET_ERR.code, 500);
        }
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @return ApiResponse indicating success or error information
     */
    @Override
    public ApiResponse<Void> deleteProduct(String id) {
        try {
            productRepository.deleteById(id);
            return new ApiResponse<>(null, ResponseStatus.SUCCESS.value, null, 204);
        } catch (Exception e) {
            return new ApiResponse<>(null, ResponseStatus.FAIL.value, ErrorCodes.PRD_DEL_ERR.code, 500);
        }
    }
}