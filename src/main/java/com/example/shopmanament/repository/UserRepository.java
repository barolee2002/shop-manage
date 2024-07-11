package com.example.shopmanament.repository;

import com.example.shopmanament.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Add custom query methods if needed
    List<User> findByStoreId(Long storeId);
    Long countAllByStoreId(Long storeId);
    Optional<User> findByCode(String code);
    Optional<User> findByUsername(String username);
    Optional<User> findFirstByUsername(String username);
    @Query("select e from User e where (:searchString is null or :searchString = '' or e.code like %:searchString% or e.name like %:searchString%)" +
            "and (:role is null or :role = '' or e.role = :role) " +
            "and (e.storeId = :storeId)"
    )
    Page<User> getAll(@Param("storeId") Long storeId, @Param("searchString") String searchString, @Param("role") String role, Pageable pageable);


    User findByConfirmToken(String token);

}