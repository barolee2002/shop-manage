package com.example.clothes.repository;

import com.example.clothes.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    // Add custom query methods if needed
}