package com.sandbox.ph.jay100.productserviceapi.Dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponseDTO {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String category;
    private String brand;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}