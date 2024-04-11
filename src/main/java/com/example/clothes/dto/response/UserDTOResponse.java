package com.example.clothes.dto.response;

import lombok.Data;

@Data
public class UserDTOResponse {
    private Long id;
    private String code;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String username;
    private String password;
    private String role;
    private Long storeId;
    private Integer status;
}
