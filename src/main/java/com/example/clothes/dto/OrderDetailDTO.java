package com.example.clothes.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private Long id;

    private Long productAttributeId;

    private Integer quantity;
    private Long oderId;
}
