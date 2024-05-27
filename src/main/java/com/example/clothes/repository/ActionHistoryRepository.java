package com.example.clothes.repository;

import com.example.clothes.entity.ActionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionHistoryRepository extends JpaRepository<ActionHistory, Long> {
}
