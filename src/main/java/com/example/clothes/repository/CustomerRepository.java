package com.example.clothes.repository;

import com.example.clothes.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Add custom query methods if needed
}