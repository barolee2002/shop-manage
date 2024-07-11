package com.example.shopmanament.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SellingStatisticDto {
    private Integer totalOrder;
    private BigDecimal totalOrderValue;
    private BigDecimal totalMoney;
}
