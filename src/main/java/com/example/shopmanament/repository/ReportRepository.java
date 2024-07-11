package com.example.shopmanament.repository;

import com.example.shopmanament.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    // Add custom query methods if needed
}