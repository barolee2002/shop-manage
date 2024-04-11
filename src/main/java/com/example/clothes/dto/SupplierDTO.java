package com.example.clothes.dto;

import jakarta.persistence.Column;
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
    private Long storeId;
    private Date createdAt;
}
