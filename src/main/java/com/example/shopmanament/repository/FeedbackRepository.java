package com.example.shopmanament.repository;

import com.example.shopmanament.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Add custom query methods if needed
}