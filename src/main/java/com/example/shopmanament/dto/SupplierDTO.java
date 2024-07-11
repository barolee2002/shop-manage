package com.example.shopmanament.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SupplierDTO {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private BigDecimal deptMoney;
    private Integer orderQuantity;
    private BigDecimal totalOrderMoney;
    private Long storeId;
    private Date createdAt;
}
