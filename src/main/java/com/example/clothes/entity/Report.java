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

    private String code;

    private Long staffId; // Representing the relationship with User

    private BigDecimal revenue;
    private Integer soldProduct;
    private Integer soldOrder;
    private BigDecimal interestMoney;
    private Date createdAt;

    // Getters and setters
}