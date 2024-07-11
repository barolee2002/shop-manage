package com.example.shopmanament.repository;

import com.example.shopmanament.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // Add custom query methods if needed
    List<Supplier> findByStoreId(Long storeId);
    @Query("SELECT e FROM Supplier e WHERE (:searchString IS NULL OR :searchString = '' or e.name like %:searchString%) " +
            "AND (e.deptMoney IS NULL OR e.deptMoney >= :fromTotal AND e.deptMoney <= :toTotal) " +
            "AND (e.storeId = :storeId) " +
            "AND e.status = 1"
    )
    Page<Supplier> getAll(
            @Param("storeId") Long storeId,
            @Param("searchString") String searchString,
            @Param("fromTotal") BigDecimal fromTotal,
            @Param("toTotal") BigDecimal toTotal,
            Pageable pageable
    );

}