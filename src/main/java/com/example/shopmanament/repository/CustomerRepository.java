package com.example.shopmanament.repository;

import com.example.shopmanament.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT distinct e FROM Customer e WHERE (:searchString IS NULL OR :searchString = '' or e.code like %:searchString% OR e.name = %:searchString% OR e.phone = %:searchString%) " +
            "AND (e.storeId = :storeId)"
    )
    Page<Customer> getAll(
            @Param("searchString") String searchString,
            @Param("storeId") Long storeId,
            Pageable pageable
    );

    // Add custom query methods if needed
    Page<Customer> findByStoreId(Long storeId, Pageable pageable);
}