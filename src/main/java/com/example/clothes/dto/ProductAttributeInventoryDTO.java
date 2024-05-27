package com.example.clothes.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductAttributeInventoryDTO {
    private Long id;
    private InventoryDto inventory;
    private Long productId;
    private BigDecimal costPrice;
    private BigDecimal sellPrice;
    private Integer quantity;

}
