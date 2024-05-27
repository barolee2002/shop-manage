package com.example.clothes.service;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.CookieDto;
import com.example.clothes.dto.GoodsReceiptDTO;

import java.math.BigDecimal;

public interface GoodsReceiptService {
    BasePage<GoodsReceiptDTO> getAll(Long storeId, Long supplierId, Long bookingUserId, Long confirmUserId, Long payStatus, Long receiptStatus, Long inventoryId, String bookingFromTime, String bookngToTime, String receiptFromTime, String receiptToTime, BigDecimal fromTotal, BigDecimal toTotal, Integer page, Integer pageSize);

    GoodsReceiptDTO create(CookieDto cookieDto, GoodsReceiptDTO request);

    GoodsReceiptDTO getDetail(Long goosReceiptId);

    Long delete(CookieDto cookieDto,Long receiptId);

    String payOrder(CookieDto cookieDto,Long orderId);

    String InventoryIn(CookieDto cookieDto,Long orderId);
}
