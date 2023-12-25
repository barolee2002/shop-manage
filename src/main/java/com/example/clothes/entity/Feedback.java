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

    private Long customerId; // Representing the relationship with Customer
    private Long userId; // Representing the relationship with User

    private String subject;
    private String content;

    private Long orderId; // Representing the relationship with SellingOrder

    private Date createAt;
    private Integer status;

    // Getters and setters
}