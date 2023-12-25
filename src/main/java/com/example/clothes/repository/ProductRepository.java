package com.example.clothes.repository;

import com.example.clothes.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Add custom query methods if needed
}