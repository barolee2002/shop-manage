package com.example.shopmanament.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;

    private String name;
    private String brand;
    private Integer status;
    private Long storeId;
    private String imageLink;
    private String category;
    private Date createAt;
    private List<ProductAttributeDTO> attributes;
}
