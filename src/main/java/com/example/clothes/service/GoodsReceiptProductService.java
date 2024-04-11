package com.example.clothes.service;

import com.example.clothes.dto.GoodsReiceptProductDTO;

import java.util.List;

public interface GoodsReceiptProductService {
    GoodsReiceptProductDTO create(GoodsReiceptProductDTO dto, Long receiptId);

    List<GoodsReiceptProductDTO> getByGoodsReceipt(Long reiceptId);
}
