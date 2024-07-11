package com.example.shopmanament.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductInventoryManamentDto {
    private Long id;
    private String code;
    private Long productId;
    private String productName;
    private String productBrand;
    private String productCategory;
    private String imageLink;
    private List<OtherAttribute> otherAttribute;
    private BigDecimal costPrice;
    private BigDecimal sellPrice;
    private Integer quantity;
}
