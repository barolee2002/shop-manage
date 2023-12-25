package com.example.clothes.repository;

import com.example.clothes.entity.InventoryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface InventoryUserRepository extends JpaRepository<InventoryUser, Long> {
    Long countAllByUserId(Long userId);
    List<InventoryUser> findByUserId(Long userId);
    List<InventoryUser> findByInventoryId(Long inventoryId);

}
