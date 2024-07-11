package com.example.shopmanament.service;

import com.example.shopmanament.dto.GoodsReiceptProductDTO;

import java.util.List;

public interface GoodsReceiptProductService {
    GoodsReiceptProductDTO create(GoodsReiceptProductDTO dto, Long receiptId);

    List<GoodsReiceptProductDTO> getByGoodsReceipt(Long reiceptId);

    Long delete(Long receiptProductId);

    String InventoryIn(Long orderId, Long inventoryId);
}
