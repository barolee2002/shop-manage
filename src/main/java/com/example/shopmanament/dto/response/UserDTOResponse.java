package com.example.shopmanament.dto.response;

import com.example.shopmanament.dto.ActionHistoryDto;
import lombok.Data;

import java.util.List;

@Data
public class UserDTOResponse {
    private Long id;
    private String code;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String username;
    private String avatar;
    private String role;
    private Long storeId;
    private List<ActionHistoryDto> actionHistories;
}
