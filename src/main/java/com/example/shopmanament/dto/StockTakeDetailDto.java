package com.example.shopmanament.dto;

import lombok.Data;

@Data
public class StockTakeDetailDto {
    private Long id;
    private Long stockTake;
    private ProductAttributeDTO productAttribute;
    private Integer oldQuantity;
    private Integer actualQuantity;
    private String reason;
}
