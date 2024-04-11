package com.example.clothes.dto.response;

import com.example.clothes.dto.ProductAttributeDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String brand;
    private Integer status;
    private Long storeId;
    private String imageLink;
    private Integer totalQuantity;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String category;
    private Date createAt;
    private List<ProductAttributeResponseDto> attributes;
}
