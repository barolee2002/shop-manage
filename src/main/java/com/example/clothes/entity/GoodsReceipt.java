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

    @Column
    private String code;

    @Column
    private Long supplierId;
    @Column
    private Long bookingUserId;
    @Column
    private Long confirmUserId;
    @Column
    private Long inventoryId;
    private Long storeId;
    @Column
    private Long paymentTypeId;

    @Column
    private BigDecimal total;
    @Column
    private Date bookingDate;
    @Column
    private Integer payStatus;
    @Column
    private Integer receiptStatus;
    @Column
    private Date receiptDate;

    // Getters and setters
}
