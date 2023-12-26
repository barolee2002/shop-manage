package com.example.clothes.repository;

import com.example.clothes.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    // Add custom query methods if needed
    List<ProductAttribute> findByProductId(Long productId);
}