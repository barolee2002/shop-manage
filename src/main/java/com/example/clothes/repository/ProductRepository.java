package com.example.clothes.repository;

import com.example.clothes.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Add custom query methods if needed
    @Query("SELECT e FROM Product e WHERE (:name IS NULL OR e.name LIKE %:name%) " +
            "AND (:status IS NULL  OR e.status = :status) " +
            "AND (:category IS NULL  OR e.category = :category) " +
            "AND (:code IS NULL  OR e.code = :status) " +
            "AND (:ownerId IS NULL  OR e.ownerId = :category) "
    )
    Page<Product> findAll(
            @Param("name") String name,
            @Param("status") Integer status,
            @Param("category") String category,
            @Param("code") String code,
            @Param("ownerId") Long ownerId,
            Pageable pageable
    );
}