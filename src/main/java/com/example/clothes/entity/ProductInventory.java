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
@Table(name = "product_inventory")
public class ProductInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productAttributeId;
    private Long inventoryId;
    private BigDecimal costPrice;
    private BigDecimal sellPrice;
    private Integer quantity;
}