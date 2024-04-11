package com.example.clothes.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private Long id;
    private ProductDTO product;

    private ProductAttributeDTO productAttribute;

    private Integer quantity;
    private Long orderId;
}
