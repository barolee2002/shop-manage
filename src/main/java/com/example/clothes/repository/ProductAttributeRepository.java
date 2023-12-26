package com.example.clothes.repository;

import com.example.clothes.entity.ProductAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    // Add custom query methods if needed

    Page<ProductAttribute> findByProductIdAndStatus(Long productId,Integer status, Pageable pageable);
}