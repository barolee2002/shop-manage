package com.example.shopmanament.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailDTO {
    private Long id;

    private ProductAttributeDTO product;
    private Integer discount;
    private Integer quantity;
    private BigDecimal price;
    private Long orderId;
}
