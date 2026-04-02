package com.sandbox.ph.jay100.productserviceapi.service;


import com.sandbox.ph.jay100.productserviceapi.Dto.ProductRequestDTO;
import com.sandbox.ph.jay100.productserviceapi.Dto.ProductResponseDTO;
import com.sandbox.ph.jay100.productserviceapi.exception.ProductNotFoundException;
import com.sandbox.ph.jay100.productserviceapi.model.Product;
import com.sandbox.ph.jay100.productserviceapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // CREATE - Add new product
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        // Check if SKU already exists
        if (productRepository.existsBySku(requestDTO.getSku())) {
            throw new RuntimeException("Product with SKU " + requestDTO.getSku() + " already exists");
        }

        Product product = mapToEntity(requestDTO);
        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    // READ - Get all products
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // READ - Get product by ID
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return mapToDTO(product);
    }

    // READ - Get product by SKU
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with SKU: " + sku));
        return mapToDTO(product);
    }

    // READ - Get products by category
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // READ - Search products by name
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // UPDATE - Update existing product
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        // Check if SKU is being changed and if new SKU already exists
        if (!existingProduct.getSku().equals(requestDTO.getSku()) &&
                productRepository.existsBySku(requestDTO.getSku())) {
            throw new RuntimeException("Product with SKU " + requestDTO.getSku() + " already exists");
        }

        updateEntity(existingProduct, requestDTO);
        Product updatedProduct = productRepository.save(existingProduct);
        return mapToDTO(updatedProduct);
    }

    // DELETE - Delete product by ID
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    // DELETE - Delete product by SKU
    @Transactional
    public void deleteProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with SKU: " + sku));
        productRepository.delete(product);
    }

    // Helper methods for mapping
    private Product mapToEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setSku(dto.getSku());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setBrand(dto.getBrand());
        return product;
    }

    private void updateEntity(Product product, ProductRequestDTO dto) {
        product.setSku(dto.getSku());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setBrand(dto.getBrand());
    }

    private ProductResponseDTO mapToDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .brand(product.getBrand())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
