package com.example.shopmanament.controller;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.GoodsReceiptDTO;
import com.example.shopmanament.dto.Response;
import com.example.shopmanament.service.GoodsReceiptService;
import com.example.shopmanament.utils.Constant;
import com.example.shopmanament.utils.JsonParse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/goods_receipt")
@RequiredArgsConstructor
public class ReceiptProductController {
    private final GoodsReceiptService goodsReceiptService;
    private final JsonParse jsonParse = new JsonParse();

    @PostMapping("/creating")
    public Response<GoodsReceiptDTO> create(@CookieValue(value = "userInfo", required = false) String cookieDto,@RequestBody GoodsReceiptDTO request) {
        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(), goodsReceiptService.create(dto,request));
    }
    @GetMapping("/detail/{id}")
    public Response<GoodsReceiptDTO> getDetail(@PathVariable("id") Long id) {
        return new Response<>(HttpStatus.OK.value(), goodsReceiptService.getDetail(id));
    }
    @GetMapping("/get-all")
    public Response<BasePage<GoodsReceiptDTO>> getAll (
            @RequestParam(required = true) Long storeId,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) Long bookingUserId,
            @RequestParam(required = false) Long confirmUserId,
            @RequestParam(required = false) Long payStatus,
            @RequestParam(required = false) Long receiptStatus,
            @RequestParam(required = true) Long inventoryId,
            @RequestParam( required = false, defaultValue = Constant.PAST_DATE) String bookingFromTime,
            @RequestParam( required = false, defaultValue = Constant.FUTURE_DATE) String bookingToTime,
            @RequestParam( required = false, defaultValue = Constant.PAST_DATE) String receiptFromTime,
            @RequestParam( required = false, defaultValue = Constant.FUTURE_DATE) String receiptToTime,
            @RequestParam( required = false, defaultValue = Constant.SMALL_NUMBER) BigDecimal fromTotal,
            @RequestParam( required = false, defaultValue = Constant.BIG_NUMBER) BigDecimal toTotal,
            @RequestParam( required = false, defaultValue = "1") Integer page,
            @RequestParam( required = false, defaultValue = "10") Integer pageSize
    ) {
        return new Response<>(HttpStatus.OK.value(), goodsReceiptService.getAll(storeId,supplierId,bookingUserId,confirmUserId,payStatus,receiptStatus,inventoryId,bookingFromTime, bookingToTime, receiptFromTime, receiptToTime, fromTotal, toTotal,page,pageSize));
    }
    @PutMapping("/updating")
    public Response<GoodsReceiptDTO> update(@CookieValue(value = "userInfo", required = false) String cookieDto, @RequestBody GoodsReceiptDTO request) {
        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(), goodsReceiptService.create(dto,request));
    }
    @PutMapping("/pay/{id}")
    public Response<String> pay(@CookieValue(value = "userInfo", required = false) String cookieDto,@PathVariable("id") Long id) {
        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(), goodsReceiptService.payOrder(dto,id));
    }
    @PutMapping("/inventory-in/{id}")
    public Response<String> inventoryIn (@CookieValue(value = "userInfo", required = false) String cookieDto,@PathVariable("id") Long id) {
        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(), goodsReceiptService.InventoryIn(dto,id));
    }
    @DeleteMapping("/delete/{id}")
    public Response<Long> delete(@CookieValue(value = "userInfo", required = false) String cookieDto,@PathVariable("id") Long id) {
        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(), goodsReceiptService.delete(dto,id));

    }
}
