package com.example.shopmanament.service;

import com.example.shopmanament.dto.StockTakeDetailDto;

import java.util.List;

public interface StockTakeDetailService {
    StockTakeDetailDto createOrUpdate(StockTakeDetailDto request, Long stockTakeId);

    List<StockTakeDetailDto> getByStockTake(Long stockTake);

    Long delete(Long id);
}
