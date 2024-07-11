package com.example.shopmanament.dto;

import com.example.shopmanament.dto.response.UserDTOResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SellingOrderDTO {
    private Long id;
    private String code;
    private CustomerDTO customer;
    private UserDTOResponse staff;
    private BigDecimal total;
    private InventoryDto inventory;
    private Integer discount;
    private Integer status;
    private Long storeId;
    private String paymentType;
    private Date createAt;
    private List<OrderDetailDTO> details;
}
