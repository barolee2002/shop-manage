package com.example.shopmanament.dto;

import lombok.Data;

@Data
public class ProductAttributeInventoryDTO {
    private Long id;
    private InventoryDto inventory;
    private Long productId;
    private Integer quantity;

}
