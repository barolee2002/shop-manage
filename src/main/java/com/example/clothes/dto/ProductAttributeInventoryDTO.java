package com.example.clothes.dto;

import com.example.clothes.dto.response.InventoryDTOResponse;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductAttributeInventoryDTO {
    private Long id;
    private InventoryDTOResponse inventory;
    private Long productId;
    private BigDecimal costPrice;
    private BigDecimal sellPrice;
    private Integer quantity;

}
