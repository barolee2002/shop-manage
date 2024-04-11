package com.example.clothes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long customerId; // Representing the relationship with Customer
    @Column
    private Long userId; // Representing the relationship with User

    @Column
    private String subject;
    @Column
    private String content;

    @Column
    private Long orderId; // Representing the relationship with SellingOrder

    @Column
    private Date createAt;
    @Column
    private Integer status;

    // Getters and setters
}