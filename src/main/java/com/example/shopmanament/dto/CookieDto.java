package com.example.shopmanament.dto;

import lombok.Data;

@Data
public class CookieDto {
    private String username;
    private String name;
    private Long storeId;
    private Long id;
    private String token;
    private Integer expireTime;
    private Long userId;
}
