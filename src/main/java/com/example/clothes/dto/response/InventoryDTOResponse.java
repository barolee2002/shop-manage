package com.example.clothes.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryDTOResponse {
    private Long id;
    private String code;
    private String name;
    private String address;
    private Date createAt;
}
