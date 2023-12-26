package com.example.clothes.dto;

import lombok.Data;

@Data

public class ProductAttributeDTO {
    private Long id;

    private Long productId;

    private String material;
    private String brand;
    private String size;
    private String origin;
    private String variation;
    private String description;
    private Integer status;
}
