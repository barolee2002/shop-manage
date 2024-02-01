package com.example.clothes.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String username;
    private String name;
    private Long ownerId;
    private Long id ;
    private String token;
    private long expireTime;
}
