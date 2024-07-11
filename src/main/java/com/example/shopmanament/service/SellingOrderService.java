package com.example.shopmanament.service;

import com.example.shopmanament.dto.*;

import java.math.BigDecimal;
import java.util.List;

public interface SellingOrderService {
    SellingOrderDTO create(CookieDto cookieDto, SellingOrderDTO orderDTO);

    BasePage<SellingOrderDTO> getAll(Long storeId, String searchString, Long staffId, Long inventoryId, String sellFromTime, String sellToTime, String paymentType, BigDecimal fromTotal, BigDecimal toTotal, Integer page, Integer pageSize);

    SellingOrderDTO getDetail(Long orderId);

    BasePage<SellingOrderDTO> getAllOrderByCustomer(Long customerId, Integer page, Integer pageSize);

    SellingStatisticDto getStatistic(Long storeId, Long inventoryId, String time);

    List<SellingTimeStatistic> getSellingStatistic(Long storeId, Long inventoryId, String time);
}
