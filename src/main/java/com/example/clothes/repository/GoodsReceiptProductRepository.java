package com.example.clothes.repository;

import com.example.clothes.entity.GoodsReceiptProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsReceiptProductRepository extends JpaRepository<GoodsReceiptProduct, Long> {
    // Add custom query methods if needed
    List<GoodsReceiptProduct> findByReceiptId(Long receiptId);
}
