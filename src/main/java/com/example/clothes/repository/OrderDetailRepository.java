package com.example.clothes.repository;

import com.example.clothes.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // Add custom query methods if needed
    List<OrderDetail> findByOrderId(Long orderId);
}
