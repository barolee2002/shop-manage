package com.example.shopmanament.repository;

import com.example.shopmanament.entity.SellingOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SellingOrderRepository extends JpaRepository<SellingOrder, Long> {
    // Add custom query methods if needed
    Optional<SellingOrder> findByCode(String code);
    Long countByStoreId(Long storeId);
    List<SellingOrder> findByStoreId(Long storeId);
    List<SellingOrder> findByCustomerId(Long customerId);

    @Query("SELECT e FROM SellingOrder e WHERE (:searchString IS NULL OR :searchString = '' OR e.code LIKE %:searchString%) " +
            "AND (:staffId IS NULL OR :staffId = 0 OR e.userId = :staffId) " +
            "AND (:paymentType IS NULL OR :paymentType = '' OR e.paymentType = :paymentType) " +
            "AND (:inventoryId IS NULL OR e.inventoryId = :inventoryId) " +
            "AND (:sellFromTime IS NULL OR :sellToTime IS NULL OR STR_TO_DATE(e.createAt, '%Y-%m-%d') BETWEEN STR_TO_DATE(:sellFromTime, '%Y-%m-%d') AND STR_TO_DATE(:sellToTime, '%Y-%m-%d')) " +
            "AND (:fromTotal IS NULL OR :toTotal IS NULL OR e.total BETWEEN :fromTotal AND :toTotal) " +
            "AND (e.storeId = :storeId)"
    )
    Page<SellingOrder> getAll(
            @Param("storeId") Long storeId,
            @Param("staffId") Long staffId,
            @Param("searchString") String searchString,
            @Param("inventoryId") Long inventoryId,
            @Param("sellFromTime") String sellFromTime,
            @Param("sellToTime") String sellToTime,
            @Param("paymentType") String paymentType,
            @Param("fromTotal") BigDecimal fromTotal,
            @Param("toTotal") BigDecimal toTotal,
            Pageable pageable
    );

    @Query("SELECT e FROM SellingOrder e WHERE (:inventoryId IS NULL OR e.inventoryId = :inventoryId) " +
            "AND (e.createAt IS NULL OR STR_TO_DATE(e.createAt, '%Y-%m-%d') >= STR_TO_DATE(:sellFromTime , '%Y-%m-%d') AND STR_TO_DATE(e.createAt, '%Y-%m-%d') <= STR_TO_DATE(:sellToTime, '%Y-%m-%d')) " +
            "AND (e.storeId = :storeId)"
    )
    List<SellingOrder> getByFilter(
            @Param("storeId") Long storeId,
            @Param("inventoryId") Long inventoryId,
            @Param("sellFromTime") String sellFromTime,
            @Param("sellToTime") String sellToTime
    );
    Page<SellingOrder> findByCustomerId(
            Long customerId,
            Pageable pageable
    );
    @Query("SELECT Date(e.createAt) AS date, SUM(e.total) AS total " +
            "FROM SellingOrder e " +
            "WHERE (STR_TO_DATE(e.createAt, '%Y-%m-%d') = STR_TO_DATE(:day , '%Y-%m-%d')) AND (e.storeId = :storeId) AND ( :inventoryId is null or :inventoryId = 0 or e.inventoryId = :inventoryId )" +
            "GROUP BY e.createAt")
    List<Object[]> getByDay(@Param("day") String day,@Param("storeId") Long storeId, @Param("inventoryId") Long inventoryId);

    @Query("select e.customerId, sum(e.total), count(e) from SellingOrder e " +
            "WHERE (e.storeId = :storeId) AND ( :inventoryId is null or :inventoryId = 0 or e.inventoryId = :inventoryId ) " +
            "and (e.createAt IS NULL OR STR_TO_DATE(e.createAt, '%Y-%m-%d') >= STR_TO_DATE(:sellFromTime , '%Y-%m-%d') AND STR_TO_DATE(e.createAt, '%Y-%m-%d') <= STR_TO_DATE(:sellToTime, '%Y-%m-%d')) " +
            "GROUP BY e.customerId " +
            "order by sum(e.total) desc limit 3"
    )
    List<Object[]> getTopCustomer(
            @Param("storeId") Long storeId,
            @Param("inventoryId") Long inventoryId,
            @Param("sellFromTime") String sellFromTime,
            @Param("sellToTime") String sellToTime
            );
    @Query("select e.customerId, sum(e.total), count(e) from SellingOrder e " +
            "WHERE (e.storeId = :storeId) AND ( :inventoryId is null or :inventoryId = 0 or e.inventoryId = :inventoryId ) " +
            "and (e.createAt IS NULL OR STR_TO_DATE(e.createAt, '%Y-%m-%d') >= STR_TO_DATE(:sellFromTime , '%Y-%m-%d') AND STR_TO_DATE(e.createAt, '%Y-%m-%d') <= STR_TO_DATE(:sellToTime, '%Y-%m-%d')) " +
            "GROUP BY e.customerId " +
            "order by count(e) desc limit 3"
    )
    List<Object[]> getTopCustomerOrder(
            @Param("storeId") Long storeId,
            @Param("inventoryId") Long inventoryId,
            @Param("sellFromTime") String sellFromTime,
            @Param("sellToTime") String sellToTime
    );

}