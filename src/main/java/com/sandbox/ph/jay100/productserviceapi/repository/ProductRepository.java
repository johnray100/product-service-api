package com.sandbox.ph.jay100.productserviceapi.repository;

import com.sandbox.ph.jay100.productserviceapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    List<Product> findByCategory(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByNameContainingIgnoreCase(String name);

    boolean existsBySku(String sku);
}
