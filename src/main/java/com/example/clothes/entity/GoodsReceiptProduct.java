package com.example.clothes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "goods_receipt_product")
public class GoodsReceiptProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long receiptId;
    @Column
    private Long productAttributeId;

    @Column
    private BigDecimal cost;
    @Column
    private Integer quantity;

    // Getters and setters
}