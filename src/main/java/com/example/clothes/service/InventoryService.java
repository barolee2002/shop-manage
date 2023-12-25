package com.example.clothes.service;

import com.example.clothes.dto.request.InventoryDTORequest;
import com.example.clothes.dto.response.InventoryDTOResponse;
import com.example.clothes.dto.response.UserDTOResponse;

import java.util.List;

public interface InventoryService {
    InventoryDTOResponse addInventory (Long userId, InventoryDTORequest inventoryDTORequest);

    InventoryDTOResponse update(Long inventoryId, InventoryDTORequest inventoryDTORequest);

    Long delete(Long inventoryId);

    List<InventoryDTOResponse> getAllByUser(Long userId);

    InventoryDTOResponse getDetail(Long inventoryId);

    List<UserDTOResponse> getAllStaffInInventory(Long inventoryId);
}
