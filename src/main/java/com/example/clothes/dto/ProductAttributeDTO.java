package com.example.clothes.dto;

import lombok.Data;

import java.util.List;

@Data

public class ProductAttributeDTO {
    private Long id;
    private String code;
    private Long productId;
    private String imageLink;
    private List<OtherAttribute> otherAttribute;
    private List<ProductAttributeInventoryDTO> inventoryList;
 }
