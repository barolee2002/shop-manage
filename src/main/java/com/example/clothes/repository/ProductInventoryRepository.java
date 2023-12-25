package com.example.clothes.repository;

import com.example.clothes.entity.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {
    // Add custom query methods if needed
}