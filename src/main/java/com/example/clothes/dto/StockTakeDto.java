package com.example.clothes.dto;

import com.example.clothes.dto.response.UserDTOResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StockTakeDto {
    private Long id;
    private Long storeId;
    private String code;
    private InventoryDto inventory;
    private UserDTOResponse createUser;
    private UserDTOResponse confirmUser;
    private Integer status;
    private List<StockTakeDetailDto> details;
    private Date createAt;
    private Date updateAt;
}
