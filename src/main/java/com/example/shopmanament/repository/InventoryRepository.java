package com.example.shopmanament.repository;

import com.example.shopmanament.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Add custom query methods if needed
    List<Inventory> findByStoreId(Long storeId);
    Optional<Inventory> findByCode(String code);
    Long countByStoreId(Long storeId);
}
