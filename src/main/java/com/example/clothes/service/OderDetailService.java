package com.example.clothes.service;

import com.example.clothes.dto.OrderDetailDTO;

import java.util.List;

public interface OderDetailService {
    OrderDetailDTO create(OrderDetailDTO orderDetailDTO, Long orderId);

    List<OrderDetailDTO> getByOrderId(Long orderId);
}
