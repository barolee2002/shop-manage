package com.example.shopmanament.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SellingTimeStatistic {
    private Date date;
    private BigDecimal total;
    public SellingTimeStatistic(Date date, BigDecimal total) {
        this.date = date;
        this.total = total;
    }
}
