package com.example.clothes.service;

import com.example.clothes.dto.ProductAttributeInventoryDTO;

import java.util.List;

public interface ProductInventoryService {
    List<ProductAttributeInventoryDTO> getAllByProductId(Long productId);

    ProductAttributeInventoryDTO create(ProductAttributeInventoryDTO dto);

    ProductAttributeInventoryDTO update(ProductAttributeInventoryDTO dto);

    ProductAttributeInventoryDTO getByProductAndInventory(Long productId, Long inventoryId);
}
