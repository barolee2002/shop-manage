package com.example.clothes.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String code;
    private String name;
    private Integer status;
    private String category;
    List<ProductAttributeDTO> attributes;
}
