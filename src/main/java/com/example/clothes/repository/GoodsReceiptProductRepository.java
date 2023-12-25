package com.example.clothes.repository;

import com.example.clothes.entity.GoodsReceiptProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsReceiptProductRepository extends JpaRepository<GoodsReceiptProduct, Long> {
    // Add custom query methods if needed
}
