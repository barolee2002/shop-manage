package com.example.clothes.repository;

import com.example.clothes.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Add custom query methods if needed
}