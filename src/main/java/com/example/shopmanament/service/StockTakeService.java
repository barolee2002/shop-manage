package com.example.shopmanament.service;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.StockTakeDto;
import jakarta.transaction.Transactional;

public interface StockTakeService {
    @Transactional
    StockTakeDto create(CookieDto cookieDto, StockTakeDto request);

    BasePage<StockTakeDto> getAll(Long storeId,String srearchString, Long confirmUser, Long createUser, Long inventoryId, Integer status, String createFromTime, String createToTime, String updateFromTime, String updateToTime, Integer page, Integer pageSize);

    StockTakeDto getDetail(Long stockTakeId);

    Long delete(CookieDto cookieDto,Long stockTakeId);

    @Transactional
    Long updateInventory(CookieDto cookieDto,Long stockTakeId);
}
