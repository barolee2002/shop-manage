package com.example.shopmanament.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ActionHistoryDto {
    private Long id;
    private Long userId;
    private String message;
    private Date createAt;

}
