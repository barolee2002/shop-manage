package com.example.shopmanament.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_take")
public class StockTake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long storeId;
    private String code;
    @Column(name = "create_user")
    private Long createUser; // Representing the relationship with User
    @Column(name = "confirm_user")
    private Long confirmUser;
    @Column
    private Integer status;
    @Column(name = "inventory_id")
    private Long inventoryId;
    @Column
    private Date createAt;
    @Column
    private Date updateAt;

    // Getters and setters
}