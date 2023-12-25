package com.example.clothes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "goods_receipt")
public class GoodsReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Long supplierId;
    private Long bookingUserId;
    private Long confirmUserId;
    private Long inventoryId;
    private Long paymentTypeId;

    private BigDecimal total;
    private Date bookingDate;
    private Integer payStatus;
    private Integer receiptStatus;
    private Date receiptDate;

    // Getters and setters
}
