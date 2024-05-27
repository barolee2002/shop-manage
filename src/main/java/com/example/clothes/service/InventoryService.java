package com.example.clothes.service;

import com.example.clothes.dto.InventoryDto;
import com.example.clothes.dto.request.InventoryDTORequest;
import com.example.clothes.dto.response.UserDTOResponse;

import java.util.List;

public interface InventoryService {
    InventoryDto addInventory (Long userId, InventoryDTORequest inventoryDTORequest);

    InventoryDto update(Long inventoryId, InventoryDTORequest inventoryDTORequest);

    Long delete(Long inventoryId);

    List<InventoryDto> getAllByUser(Long userId);

    InventoryDto getDetail(Long inventoryId);

    List<UserDTOResponse> getAllStaffInInventory(Long inventoryId);

    List<InventoryDto> getByStoreId(Long storeId);
}
