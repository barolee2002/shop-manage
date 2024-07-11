package com.example.shopmanament.dto;

import com.example.shopmanament.dto.response.UserDTOResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StockTakeDto {
    private Long id;
    private Long storeId;
    private String code;
    private InventoryDto inventory;
    private UserDTOResponse create;
    private UserDTOResponse confirm;
    private Integer status;
    private List<StockTakeDetailDto> details;
    private Date createAt;
    private Date updateAt;
}
