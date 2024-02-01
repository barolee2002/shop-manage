package com.example.clothes.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String code;
    private String name;
    private String brand;
    private Integer status;
    private Integer ownerId;
    private String category;
    private Date createAt;
    private List<String> imageLinks;
    private List<ProductAttributeDTO> attributes;
}
