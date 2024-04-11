package com.example.clothes.repository;

import com.example.clothes.entity.SellingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellingOrderRepository extends JpaRepository<SellingOrder, Long> {
    // Add custom query methods if needed

    Long countByStoreId(Long storeId);
    List<SellingOrder> findByStoreId(Long storeId);
}