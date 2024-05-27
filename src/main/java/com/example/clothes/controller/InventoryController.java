package com.example.clothes.controller;

import com.example.clothes.dto.InventoryDto;
import com.example.clothes.dto.Response;
import com.example.clothes.dto.request.InventoryDTORequest;
import com.example.clothes.dto.response.UserDTOResponse;
import com.example.clothes.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @PostMapping("/{userId}")
    public Response<InventoryDto> create(
            @PathVariable("userId") Long userId,
            @RequestBody InventoryDTORequest inventoryDTO) {
        return new Response<>(HttpStatus.OK.value(),inventoryService.addInventory(userId, inventoryDTO));
    }
    @PutMapping("/update/{inventoryId}")
    public Response<InventoryDto> update(
            @PathVariable("inventoryId") Long inventoryId,
            @RequestBody InventoryDTORequest inventoryDTO) {
        return new Response<>(HttpStatus.OK.value(),inventoryService.update(inventoryId, inventoryDTO));
    }
    @PutMapping("/delete/{inventoryId}")
    public Response<Long> delete (
            @PathVariable("inventoryId") Long inventoryId
    ) {
        return new Response<>(HttpStatus.OK.value(),inventoryService.delete(inventoryId));
    }
    @GetMapping("/get-all/{storeId}")
    public Response<List<InventoryDto>> getAllInventory(@PathVariable Long storeId) {
        return new Response<>(HttpStatus.OK.value(), inventoryService.getByStoreId(storeId));
    }
    @GetMapping("/{inventoryId}/staffs")
    public Response<List<UserDTOResponse>> getAllStaffs (@PathVariable("inventoryId") Long inventoryId) {
        return new Response<>(HttpStatus.OK.value(),inventoryService.getAllStaffInInventory(inventoryId));
    }
    @GetMapping("/detail/{inventoryId}")
    public Response<InventoryDto> getDetail(@PathVariable("inventoryId") Long inventoryId) {
        return new Response<>(HttpStatus.OK.value(),inventoryService.getDetail(inventoryId));
    }
}
