package com.example.clothes.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class UserInventoryKey implements Serializable {
    private Long userId;
    private Long inventoryId;
    public UserInventoryKey(Long userId, Long inventoryId) {
        this.userId = userId;
        this.inventoryId = inventoryId;
    }

    public UserInventoryKey() {

    }
}
