package com.example.clothes.repository;

import com.example.clothes.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // Add custom query methods if needed
}