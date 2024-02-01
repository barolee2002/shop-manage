package com.example.clothes.service;

import com.example.clothes.dto.ProductAttributeInventoryDTO;

import java.util.List;

public interface ProductInventoryService {
    List<ProductAttributeInventoryDTO> getAllByProductId(Long productId);
}
