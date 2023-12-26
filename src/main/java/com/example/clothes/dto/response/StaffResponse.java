package com.example.clothes.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class StaffResponse {
    private Long Id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String username;
    private String password;
    private String role;
    private Long ownerId;
    private Date createAt;
}
