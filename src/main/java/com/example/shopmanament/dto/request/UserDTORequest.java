package com.example.shopmanament.dto.request;

import lombok.Data;

@Data
public class UserDTORequest {
    private String code;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String username;
    private String avatar;
    private Long storeId;
    private Long inventoryId;
    private String password;
    private String role;
}
