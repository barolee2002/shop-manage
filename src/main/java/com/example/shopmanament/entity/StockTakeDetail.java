package com.example.shopmanament.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_take_detail")
public class StockTakeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long stockTake;
    @Column
    private Long productId;

    @Column
    private Integer oldQuantity;
    @Column
    private Integer actualQuantity;
    @Column
    private String reason;
}