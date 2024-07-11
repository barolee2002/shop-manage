package com.example.shopmanament.repository;

import com.example.shopmanament.entity.StockTake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockTakeRepository extends JpaRepository<StockTake, Long> {
    // Add custom query methods if needed
    Long countAllByStoreId(Long storeId);
    Optional<StockTake> findByCode(String code);
    @Query("SELECT e FROM StockTake e WHERE (:confirmUser IS NULL OR e.confirmUser = :confirmUser) " +
            "AND (:createUser IS NULL OR e.createUser = :createUser) " +
            "AND (:searchString IS NULL OR :searchString = '' OR e.code LIKE %:searchString%)" +
            "AND (:inventoryId IS NULL OR e.inventoryId = :inventoryId) " +
            "AND (:status IS NULL OR e.status = :status) " +
            "AND (e.createAt IS NULL OR STR_TO_DATE(e.createAt, '%Y-%m-%d') BETWEEN STR_TO_DATE(:createFromTime , '%Y-%m-%d') AND STR_TO_DATE(:createToTime, '%Y-%m-%d')) " +
            "AND (e.updateAt IS NULL OR STR_TO_DATE(e.updateAt, '%Y-%m-%d') BETWEEN STR_TO_DATE(:updateFromTime , '%Y-%m-%d') AND STR_TO_DATE(:updateToTime, '%Y-%m-%d')) " +
            "AND (e.storeId = :storeId)"
    )
    Page<StockTake> getAll(
            @Param("storeId") Long storeId,
            @Param("searchString") String searchString,
            @Param("createUser") Long createUser,
            @Param("confirmUser") Long confirmUser,
            @Param("inventoryId") Long inventoryId,
            @Param("status") Integer status,
            @Param("createFromTime") String createFromTime,
            @Param("createToTime") String createToTime,
            @Param("updateFromTime") String updateFromTime,
            @Param("updateToTime") String updateToTime,
            Pageable pageable
    );
}