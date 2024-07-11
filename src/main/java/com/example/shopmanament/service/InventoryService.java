package com.example.shopmanament.service;

import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.InventoryDto;
import com.example.shopmanament.dto.request.InventoryDTORequest;
import com.example.shopmanament.dto.response.UserDTOResponse;

import java.util.List;

public interface InventoryService {
    InventoryDto addInventory (CookieDto cookieDto, Long storeId, InventoryDTORequest inventoryDTORequest);

    InventoryDto update(CookieDto cookieDto,Long inventoryId, InventoryDTORequest inventoryDTORequest);

    Long delete(CookieDto cookieDto,Long inventoryId);

    List<InventoryDto> getAllByUser(Long userId);

    InventoryDto getDetail(Long inventoryId);

    List<UserDTOResponse> getAllStaffInInventory(Long inventoryId);

    List<InventoryDto> getByStoreId(Long storeId);
}
