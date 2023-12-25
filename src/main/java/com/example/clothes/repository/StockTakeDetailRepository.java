package com.example.clothes.repository;

import com.example.clothes.entity.StockTakeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTakeDetailRepository extends JpaRepository<StockTakeDetail, Long> {
    // Add custom query methods if needed
}