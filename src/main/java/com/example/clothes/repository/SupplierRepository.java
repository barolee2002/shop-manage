package com.example.clothes.repository;

import com.example.clothes.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // Add custom query methods if needed
    List<Supplier> findByStoreId(Long storeId);
}