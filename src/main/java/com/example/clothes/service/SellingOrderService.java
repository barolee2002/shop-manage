package com.example.clothes.service;

import com.example.clothes.dto.SellingOrderDTO;

import java.util.List;

public interface SellingOrderService {
    SellingOrderDTO create(SellingOrderDTO orderDTO);

    List<SellingOrderDTO> getAll(Long storeId);

    SellingOrderDTO getDetail(Long orderId);
}
