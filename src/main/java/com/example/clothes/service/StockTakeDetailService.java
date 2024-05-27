package com.example.clothes.service;

import com.example.clothes.dto.StockTakeDetailDto;
import com.example.clothes.entity.StockTakeDetail;

import java.util.List;

public interface StockTakeDetailService {
    StockTakeDetailDto createOrUpdate(StockTakeDetailDto request, Long stockTakeId);

    List<StockTakeDetailDto> getByStockTake(Long stockTake);

    Long delete(Long id);
}
