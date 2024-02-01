package com.example.clothes.repository;

import com.example.clothes.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Add custom query methods if needed
    @Query("SELECT e FROM Product e WHERE (:name IS NULL OR :name = '' OR e.name LIKE %:name%) " +
            "AND (:status IS NULL OR e.status = :status) " +
            "AND (:category IS NULL OR :category = '' OR e.category LIKE :category)" +
            "AND (:ownerId IS NULL  OR e.ownerId = :ownerId) " +
            " AND ( STR_TO_DATE(e.createAt, '%Y-%m-%d') BETWEEN STR_TO_DATE(:fromTime , '%Y-%m-%d') AND STR_TO_DATE(:toTime, '%Y-%m-%d')) "+
            "OR (:code IS NULL OR :code = ''  OR e.code LIKE %:code%) "
    )
    Page<Product> findAll(
            @Param("name") String name,
            @Param("status") Integer status,
            @Param("category") String category,
            @Param("code") String code,
            @Param("ownerId") Long ownerId,
            @Param("fromTime") String fromTime,
            @Param("toTime") String toTime,
            Pageable pageable
    );
    @Query("SELECT e.category FROM Product e WHERE (e.ownerId = :ownerId)")
    List<String> findCategoryByOwnerId(Long ownerId);
}