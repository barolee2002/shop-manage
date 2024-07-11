package com.example.shopmanament.repository;

import com.example.shopmanament.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Add custom query methods if needed
    @Query("SELECT DISTINCT e FROM Product e LEFT JOIN ProductAttribute pa ON e.id = pa.productId WHERE " +
            "((:searchString IS NULL OR :searchString = '') OR e.name LIKE %:searchString%) " +
            "OR ((:searchString IS NULL OR :searchString = '') OR pa.code LIKE %:searchString%) " +
            "AND (e.status = 1) " +
            "AND (:category IS NULL OR :category = '' OR e.category LIKE :category) " +
            "AND (:storeId IS NULL OR e.storeId = :storeId) " +
            "AND (STR_TO_DATE(e.createAt, '%Y-%m-%d') BETWEEN STR_TO_DATE(:fromTime, '%Y-%m-%d') AND STR_TO_DATE(:toTime, '%Y-%m-%d'))"
    )
    Page<Product> findAll(
            @Param("searchString") String searchString,
            @Param("category") String category,
            @Param("storeId") Long storeId,
            @Param("fromTime") String fromTime,
            @Param("toTime") String toTime,
            Pageable pageable
    );
    @Query("SELECT DISTINCT e.category FROM Product e WHERE (e.storeId = :storeId)")
    List<String> findCategoryByStoreId(@Param("storeId") Long storeId);
    @Query("SELECT DISTINCT e.brand FROM Product e WHERE (e.storeId = :storeId)")
    List<String> findBrandByStoreId(@Param("storeId") Long storeId);

}