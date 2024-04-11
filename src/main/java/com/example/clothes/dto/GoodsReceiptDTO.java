package com.example.clothes.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsReceiptDTO {
    private Long id;
    private String code;
    private Long supplierId;
    private Long bookingUserId;
    private Long confirmUserId;
    private Long inventoryId;
    private Long storeId;
    private Long paymentTypeId;
    private BigDecimal total;
    private Date bookingDate;
    private Integer payStatus;
    private Integer receiptStatus;
    private Date receiptDate;
}
