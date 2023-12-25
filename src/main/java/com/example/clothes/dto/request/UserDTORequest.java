package com.example.clothes.dto.request;

import lombok.Data;

@Data
public class UserDTORequest {
    private String code;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String username;
    private String password;
    private String role;
}
