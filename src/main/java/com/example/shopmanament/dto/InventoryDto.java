package com.example.shopmanament.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryDto {
    private Long id;
    private String code;
    private String name;
    private Long storeId;
    private String address;
    private Date createAt;
}
