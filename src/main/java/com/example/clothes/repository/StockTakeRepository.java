package com.example.clothes.repository;

import com.example.clothes.entity.StockTake;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTakeRepository extends JpaRepository<StockTake, Long> {
    // Add custom query methods if needed
}