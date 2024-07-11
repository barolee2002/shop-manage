package com.example.shopmanament.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_user")
public class InventoryUser {
    @EmbeddedId
    private UserInventoryKey userInventoryKey;
}
