package com.example.clothes.repository;

import com.example.clothes.entity.StockTakeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface StockTakeDetailRepository extends JpaRepository<StockTakeDetail, Long> {
    // Add custom query methods if needed
    List<StockTakeDetail> findByStockTake(Long stokeId);
}