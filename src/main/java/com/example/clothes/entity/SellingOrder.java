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
@Table(name = "selling_order")
public class SellingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;
    private Long storeId;
    @Column
    private Long customerId;
    @Column
    private Long userId;

    @Column
    private BigDecimal total;
    @Column
    private Integer status;
    @Column
    private Date createAt;

    // Getters and setters
}