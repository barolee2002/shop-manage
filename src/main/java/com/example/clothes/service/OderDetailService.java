package com.example.clothes.service;

import com.example.clothes.dto.OrderDetailDTO;

public interface OderDetailService {
    OrderDetailDTO create(OrderDetailDTO orderDetailDTO, Long orderId);
}
