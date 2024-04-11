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
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private BigDecimal deptMoney;
    @Column
    private Long storeId;
    @Column
    private Integer status;
    @Column
    private Date createdAt;
}