package com.sandbox.ph.jay100.productserviceapi.controller;

import com.sandbox.ph.jay100.productserviceapi.Dto.ProductRequestDTO;
import com.sandbox.ph.jay100.productserviceapi.Dto.ProductResponseDTO;
import com.sandbox.ph.jay100.productserviceapi.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow CORS for frontend
public class ProductController {

    private final ProductService productService;

    // CREATE - Add new product
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequest) {
        ProductResponseDTO createdProduct = productService.createProduct(productRequest);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // READ - Get all products
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // READ - Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // READ - Get product by SKU
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductResponseDTO> getProductBySku(@PathVariable String sku) {
        ProductResponseDTO product = productService.getProductBySku(sku);
        return ResponseEntity.ok(product);
    }

    // READ - Get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable String category) {
        List<ProductResponseDTO> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    // READ - Search products by name
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam String name) {
        List<ProductResponseDTO> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    // UPDATE - Update product
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO productRequest) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    // DELETE - Delete product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE - Delete product by SKU
    @DeleteMapping("/sku/{sku}")
    public ResponseEntity<Void> deleteProductBySku(@PathVariable String sku) {
        productService.deleteProductBySku(sku);
        return ResponseEntity.noContent().build();
    }
}
