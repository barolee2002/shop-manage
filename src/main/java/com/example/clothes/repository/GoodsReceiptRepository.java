package com.example.clothes.repository;

import com.example.clothes.entity.GoodsReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsReceiptRepository extends JpaRepository<GoodsReceipt, Long> {
    // Add custom query methods if needed
}