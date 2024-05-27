package com.example.clothes.repository;

import com.example.clothes.entity.GoodsReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface GoodsReceiptRepository extends JpaRepository<GoodsReceipt, Long> {
    // Add custom query methods if needed

    @Query("SELECT e FROM GoodsReceipt e WHERE (:supplierId IS NULL OR e.supplierId = :supplierId) " +
            "AND (:bookingUserId IS NULL OR e.bookingUserId = :bookingUserId) " +
            "AND (:confirmUserId IS NULL OR e.confirmUserId = :confirmUserId) " +
            "AND (:inventoryId IS NULL OR e.inventoryId = :inventoryId) " +
            "AND (:payStatus IS NULL OR e.payStatus = :payStatus) " +
            "AND (:receiptStatus IS NULL OR e.receiptStatus = :receiptStatus) " +
            "AND (e.bookingDate IS NULL OR STR_TO_DATE(e.bookingDate, '%Y-%m-%d') BETWEEN STR_TO_DATE(:bookingFromTime , '%Y-%m-%d') AND STR_TO_DATE(:bookingToTime, '%Y-%m-%d')) " +
            "AND (e.receiptDate IS NULL OR STR_TO_DATE(e.receiptDate, '%Y-%m-%d') BETWEEN STR_TO_DATE(:receiptFromTime , '%Y-%m-%d') AND STR_TO_DATE(:receiptToTime, '%Y-%m-%d')) " +
            "AND (e.total IS NULL OR e.total >= :fromTotal AND e.total <= :toTotal) " +
            "AND (e.storeId = :storeId)"
    )
    Page<GoodsReceipt> getAll(
            @Param("storeId") Long storeId,
            @Param("supplierId") Long supplierId,
            @Param("bookingUserId") Long bookingUserId,
            @Param("confirmUserId") Long confirmUserId,
            @Param("inventoryId") Long inventoryId,
            @Param("payStatus") Long payStatus,
            @Param("receiptStatus") Long receiptStatus,
            @Param("bookingFromTime") String bookingFromTime,
            @Param("bookingToTime") String bookingToTime,
            @Param("receiptFromTime") String receiptFromTime,
            @Param("receiptToTime") String receiptToTime,
            @Param("fromTotal") BigDecimal fromTotal,
            @Param("toTotal") BigDecimal toTotal,
            Pageable pageable
    );
}