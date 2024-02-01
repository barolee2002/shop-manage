package com.example.clothes.repository;

import com.example.clothes.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // Add custom query methods if needed
    List<Image> findByProductId(Long productId);
    void deleteByProductId(Long productId);
}