package com.example.shopmanament.entity;


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
@Table(name = "product_attribute")
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String code;
    @Column
    private Long productId;
    @Column
    private String imageLink;
    @Column
    private String otherAttribute;
    @Column
    private BigDecimal costPrice;
    @Column
    private BigDecimal sellPrice;
    @Column
    private Integer status;
    @Column
    private Date createAt;

}