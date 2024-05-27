package com.example.clothes.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsReiceptProductDTO {
    private Long id;
    private Long receiptId;
    private ProductAttributeDTO productAttribute;
    private BigDecimal cost;
    private Integer quantity;

}
