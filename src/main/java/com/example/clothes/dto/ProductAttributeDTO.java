package com.example.clothes.dto;

import lombok.Data;

import java.util.List;

@Data

public class ProductAttributeDTO {
    private Long id;

    private Long productId;
    private String imageLink;
    private String material;
    private String size;
    private String origin;
    private String variation;
    private String description;
    private List<ProductAttributeInventoryDTO> inventoryList;
 }
