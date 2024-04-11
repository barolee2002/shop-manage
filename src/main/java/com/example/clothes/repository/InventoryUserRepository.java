package com.example.clothes.repository;

import com.example.clothes.entity.InventoryUser;
import com.example.clothes.entity.UserInventoryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface InventoryUserRepository extends JpaRepository<InventoryUser, UserInventoryKey> {
    @Query("SELECT COUNT(e) FROM InventoryUser e WHERE e.userInventoryKey.userId = :userId")
    Long countAllByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM InventoryUser e WHERE e.userInventoryKey.userId = :userId")
    List<InventoryUser> findByUserId(@Param("userId") Long userId);
    @Query("SELECT e FROM InventoryUser e WHERE e.userInventoryKey.userId = :inventoryId")

    List<InventoryUser> findByInventoryId(@Param("inventoryId") Long inventoryId);

}
