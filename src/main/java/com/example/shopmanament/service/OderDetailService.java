package com.example.shopmanament.service;

import com.example.shopmanament.dto.OrderDetailDTO;

import java.math.BigDecimal;
import java.util.List;

public interface OderDetailService {
    BigDecimal totalOrderValueByOrder(Long orderId, Long inventoryId);

    BigDecimal totalOrderMoney(Long orderId, Long inventoryId);

    OrderDetailDTO create(OrderDetailDTO orderDetailDTO, Long orderId);

    List<OrderDetailDTO> getByOrderId(Long orderId);
}
