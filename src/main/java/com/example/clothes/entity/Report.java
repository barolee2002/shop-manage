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

@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;

    @Column
    private Long staffId; // Representing the relationship with User
    @Column

    private BigDecimal revenue;
    @Column
    private Integer soldProduct;
    @Column
    private Integer soldOrder;
    @Column
    private BigDecimal interestMoney;
    @Column
    private Date createdAt;

    // Getters and setters
}