package com.example.shopmanament.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerStatisticDto {
    private CustomerDTO customer;
    private Long totalOrder;
    private BigDecimal totalExpenditure;
    public CustomerStatisticDto (CustomerDTO customer, BigDecimal totalExpenditure, Long totalOrder) {
        this.customer = customer;
        this.totalExpenditure = totalExpenditure;
        this.totalOrder = totalOrder;
    }
}
