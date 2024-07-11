package com.example.shopmanament.controller;

import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.InventoryDto;
import com.example.shopmanament.dto.Response;
import com.example.shopmanament.dto.request.InventoryDTORequest;
import com.example.shopmanament.dto.response.UserDTOResponse;
import com.example.shopmanament.service.InventoryService;
import com.example.shopmanament.utils.JsonParse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final JsonParse jsonParse = new JsonParse();
    private final InventoryService inventoryService;
    @PostMapping("/{storeId}")
    public Response<InventoryDto> create(
            @CookieValue(value = "userInfo", required = false) String cookieDto,
            @PathVariable("storeId") Long storeId,
            @RequestBody InventoryDTORequest inventoryDTO) {
        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(),inventoryService.addInventory(dto,storeId, inventoryDTO));
    }
    @PutMapping("/update/{inventoryId}")
    public Response<InventoryDto> update(
            @CookieValue(value = "userInfo", required = false) String cookieDto,
            @PathVariable("inventoryId") Long inventoryId,
            @RequestBody InventoryDTORequest inventoryDTO) {
        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(),inventoryService.update(dto,inventoryId, inventoryDTO));
    }
    @PutMapping("/delete/{inventoryId}")
    public Response<Long> delete (
            @CookieValue(value = "userInfo", required = false) String cookieDto,
            @PathVariable("inventoryId") Long inventoryId
    ) {

        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(),inventoryService.delete(dto,inventoryId));
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
