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
    List<ProductAttribute> findByProductIdAndStatus(Long productId,Integer status);
    @Query("Select e.size FROM ProductAttribute e Where (e.productId = :productId) AND (e.status = 1)")
    List<String> findSizeByProductId(Long productId);
    @Query("Select e.origin FROM ProductAttribute e Where (e.productId = :productId) AND (e.status = 1)")
    List<String> findOriginByProductId(Long productId);
    @Query("Select e.variation FROM ProductAttribute e Where (e.productId = :productId) AND (e.status = 1)")
    List<String> findColorByProductId(Long productId);
    @Query("Select e.material FROM ProductAttribute e Where (e.productId = :productId) AND (e.status = 1)")
    List<String> findMaterialByProductId(Long productId);
    @Query("Select e FROM ProductAttribute e Where (e.productId = :productId) AND (e.size = :size) AND (e.material = :material) AND (e.variation = :variation) AND (e.origin = :origin) AND (e.status = 1)")
    ProductAttribute findDupplicateAttribute(
            @Param("productId") Long productId,
            @Param("size") String size,
            @Param("material") String material,
            @Param("variation") String variation,
            @Param("origin") String origin

    );
}