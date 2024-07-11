package com.example.shopmanament.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data

public class ProductAttributeDTO {
    private Long id;
    private String code;
    private Long productId;
    private String productName;
    private String imageLink;
    private Integer systemQuantity;
    private BigDecimal costPrice;
    private BigDecimal sellPrice;
    private List<OtherAttribute> otherAttribute;
    private List<ProductAttributeInventoryDTO> inventoryList;
 }
