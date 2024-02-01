package com.example.clothes.dto;

import lombok.Data;

import java.util.Date;
@Data
public class CustomerDTO {
    private Long id;
    private String code;
    private String name;
    private String phone;
    private Long storeId;
    private Date createdAt;
}
