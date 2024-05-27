package com.example.clothes.controller;

import com.example.clothes.dto.*;
import com.example.clothes.service.StockTakeService;
import com.example.clothes.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/stock-take")
@RequiredArgsConstructor
public class StockTakeController {
    private final StockTakeService service;
    @PostMapping("/creating")
    public Response<StockTakeDto> create(@CookieValue(value = "userInfo", required = false) CookieDto cookieDto, @RequestBody StockTakeDto request) {
        return new Response<>(HttpStatus.OK.value(), service.create(cookieDto,request));
    }
    @GetMapping("/detail/{id}")
    public Response<StockTakeDto> getDetail(@PathVariable("id") Long id) {
        return new Response<>(HttpStatus.OK.value(), service.getDetail(id));
    }
    @PutMapping("/updating")
    public Response<StockTakeDto> update(@CookieValue(value = "userInfo", required = false) CookieDto cookieDto, @RequestBody StockTakeDto request) {
        return new Response<>(HttpStatus.OK.value(), service.create(cookieDto,request));
    }
    @DeleteMapping("/delete/{id}")
    public Response<Long> delete(@CookieValue(value = "userInfo", required = false) CookieDto cookieDto, @PathVariable("id") Long id) {
        return new Response<>(HttpStatus.OK.value(), service.delete(cookieDto,id));

    }
    @GetMapping("/get-all")
    public Response<BasePage<StockTakeDto>> getAll (
            @RequestParam(required = true) Long storeId,
            @RequestParam(required = false) Long confirmUser,
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) Long createUser,
            @RequestParam(required = false) Long inventoryId,
            @RequestParam(required = false) Integer status,
            @RequestParam( required = false, defaultValue = Constant.PAST_DATE) String createFromTime,
            @RequestParam( required = false, defaultValue = Constant.FUTURE_DATE) String createToTime,
            @RequestParam( required = false, defaultValue = Constant.PAST_DATE) String updateFromTime,
            @RequestParam( required = false, defaultValue = Constant.FUTURE_DATE) String updateToTime,
            @RequestParam( required = false, defaultValue = "1") Integer page,
            @RequestParam( required = false, defaultValue = "10") Integer pageSize
    ) {
        return new Response<>(HttpStatus.OK.value(), service.getAll(storeId,searchString,confirmUser,createUser,inventoryId,status,createFromTime,createToTime, updateFromTime, updateToTime,page,pageSize));
    }
    @PutMapping("/inventory/{id}")
    public Response<Long> updateInventory (@CookieValue(value = "userInfo", required = false) CookieDto cookieDto,@PathVariable("id") Long id) {
        return new Response<>(HttpStatus.OK.value(), service.updateInventory(cookieDto,id));
    }
}
