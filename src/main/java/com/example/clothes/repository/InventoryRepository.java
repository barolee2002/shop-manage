package com.example.clothes.repository;

import com.example.clothes.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Add custom query methods if needed

}
