package com.example.clothes.dto.response;

import com.example.clothes.dto.OtherAttribute;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class ProductAttributeResponseDto {
    private Long id;
    private String code;
    private Long productId;
    private String imageLink;
    private BigDecimal costPrice;
    private BigDecimal sellPrice;
    private Integer quantity;
    private List<OtherAttribute> otherAttribute;
}
