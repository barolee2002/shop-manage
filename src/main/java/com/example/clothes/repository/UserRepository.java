package com.example.clothes.repository;

import com.example.clothes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Add custom query methods if needed
    Long countAllById(Long id);
    Long countAllByOwnerId(Long owner);
    Optional<User> findByUsername(String username);
    Optional<User> findFirstByUsername(String username);
}