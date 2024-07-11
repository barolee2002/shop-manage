package com.example.shopmanament.repository;

import com.example.shopmanament.entity.ProductInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {
    // Add custom query methods if needed
    List<ProductInventory> findByProductId(Long productId);
    ProductInventory findByProductIdAndInventoryId(Long productId, Long inventoryId);
    @Query("SELECT pi " +
            "FROM ProductInventory pi " +
            "JOIN ProductAttribute pa ON pi.productId = pa.id " +
            "JOIN Product p ON pa.productId = p.id " +
            "WHERE (:searchString IS NULL OR :searchString = '' " +
            "OR p.name LIKE CONCAT('%', :searchString, '%') " +
            "OR pa.code LIKE CONCAT('%', :searchString, '%')) " +
            "AND (pi.inventoryId = :inventoryId )" +
            "AND (pi.quantity <= :quantity ) "+
            "AND (pi.quantity >= :minQuantity) " +
            "AND (:time is null or :time = '' or STR_TO_DATE(pi.updateAt, '%Y-%m-%d') <= STR_TO_DATE(:time, '%Y-%m-%d'))"
    )
    Page<ProductInventory> inventoryMnament(
            @Param("inventoryId") Long inventoryId,
            @Param("searchString") String searchString,
            @Param("time") String time,
            @Param("minQuantity") Integer minQuantity,
            @Param("quantity") Integer quantity,
            Pageable pageable
    );


}