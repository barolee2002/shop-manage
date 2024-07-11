package com.example.shopmanament.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class CustomerDTO {
    private Long id;
    private String code;
    private String name;
    private String phone;
    private Integer totalOrder;
    private BigDecimal totalMoney;
    private Long storeId;
    private Date createdAt;
}
