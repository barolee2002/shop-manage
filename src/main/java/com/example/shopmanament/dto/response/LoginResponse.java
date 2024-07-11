package com.example.shopmanament.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String username;
    private String name;
    private Long storeId;
    private Long id ;
    private String token;
    private long expireTime;
}
