package com.example.shopmanament.repository;

import com.example.shopmanament.entity.ProductAttribute;
import com.example.shopmanament.entity.StockTake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    @Query("select max(e.costPrice) from ProductAttribute e where e.productId = :productId")
    BigDecimal maxCostPrice(@Param("productId") Long productId);
    @Query("select min(e.costPrice) from ProductAttribute e where e.productId = :productId")
    BigDecimal minCostPrice(@Param("productId") Long productId);
    Optional<StockTake> findByCode(String code);
}