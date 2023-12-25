package com.example.clothes.repository;

import com.example.clothes.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    // Add custom query methods if needed
}