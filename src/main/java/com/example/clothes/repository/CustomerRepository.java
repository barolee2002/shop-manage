package com.example.clothes.repository;

import com.example.clothes.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Add custom query methods if needed
    Page<Customer> findByStoreId(Long storeId, Pageable pageable);
}