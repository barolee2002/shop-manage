package com.example.clothes.repository;

import com.example.clothes.entity.ProductAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    // Add custom query methods if needed

    Page<ProductAttribute> findByProductIdAndStatus(Long productId,Integer status, Pageable pageable);
    @Query("SELECT e FROM ProductAttribute e WHERE e.productId = :productId AND e.status = 1")
    List<ProductAttribute> findByProductIdAndStatus(@Param("productId") Long productId);


    @Query("SELECT count(e) FROM ProductAttribute e WHERE e.productId = :productId")
    Long countByProductId( @Param("productId") Long productId);
    List<ProductAttribute> findByProductId(Long productId);

    @Query("SELECT e FROM ProductAttribute e WHERE (:searchString IS NULL OR :searchString = '' OR e.code LIKE %:searchString%) " +
            "AND e.productId = :productId"
    )
    List<ProductAttribute> getByProductIdAndCode(
            @Param("searchString") String searchString,
            @Param("productId") Long productId
    );

}