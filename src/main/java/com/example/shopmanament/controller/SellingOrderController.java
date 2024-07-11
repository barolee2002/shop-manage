package com.example.shopmanament.controller;

import com.example.shopmanament.dto.*;
import com.example.shopmanament.service.SellingOrderService;
import com.example.shopmanament.utils.Constant;
import com.example.shopmanament.utils.JsonParse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/selling-order")
@RequiredArgsConstructor
public class SellingOrderController {
    private final SellingOrderService sellingOrderService;
    private final JsonParse jsonParse = new JsonParse();
    @PostMapping("/creating")
    public Response<SellingOrderDTO> create(@CookieValue(value = "userInfo", required = false) String cookieDto,@RequestBody SellingOrderDTO sellingOrderDTO) {
        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(), sellingOrderService.create(dto,sellingOrderDTO));
    }
    @GetMapping("/list-all/{storeId}")
    public Response<BasePage<SellingOrderDTO>> getAll (
            @PathVariable Long storeId,
            @RequestParam(required = false) Long staffId,
            @RequestParam(required = false) String paymentType,
            @RequestParam(required = true) Long inventoryId,
            @RequestParam(required = false)  String searchString,
            @RequestParam( required = false, defaultValue = Constant.PAST_DATE) String sellFromTime,
            @RequestParam( required = false, defaultValue = Constant.FUTURE_DATE) String sellToTime,
            @RequestParam( required = false, defaultValue = Constant.SMALL_NUMBER) BigDecimal fromTotal,
            @RequestParam( required = false, defaultValue = Constant.BIG_NUMBER) BigDecimal toTotal,
            @RequestParam( required = false, defaultValue = "1") Integer page,
            @RequestParam( required = false, defaultValue = "10") Integer pageSize
    ) {
        return new Response<>(HttpStatus.OK.value(), sellingOrderService.getAll(storeId, searchString ,staffId, inventoryId, sellFromTime, sellToTime, paymentType, fromTotal,toTotal,page,pageSize));
    }
    @GetMapping("/customer/{customerId}")
    public Response<BasePage<SellingOrderDTO>> getAll (
            @PathVariable Long customerId,
            @RequestParam( required = false, defaultValue = "1") Integer page,
            @RequestParam( required = false, defaultValue = "10") Integer pageSize
    ) {
        return new Response<>(HttpStatus.OK.value(), sellingOrderService.getAllOrderByCustomer(customerId,page,pageSize));
    }
    @GetMapping("/get-detail/{orderId}")
    public Response<SellingOrderDTO> getDetail (@PathVariable Long orderId) {
        return new Response<>(HttpStatus.OK.value(), sellingOrderService.getDetail(orderId));
    }
    @GetMapping("/statistic/{storeId}")
    public Response<SellingStatisticDto> getStatistic (
            @PathVariable Long storeId,
            @RequestParam( required = true) Long inventoryId,
            @RequestParam( required = false, defaultValue = "WEEK") String time
    ) {
        return new Response<>(HttpStatus.OK.value(), sellingOrderService.getStatistic(storeId,inventoryId,time));
    }
    @GetMapping("/statistic-detail/{storeId}")
    public Response<List<SellingTimeStatistic>> getSellingStatistic (
            @PathVariable Long storeId,
            @RequestParam( required = true) Long inventoryId,
            @RequestParam( required = false, defaultValue = "WEEK") String time
    ) {
        return new Response<>(HttpStatus.OK.value(), sellingOrderService.getSellingStatistic(storeId,inventoryId,time));
    }

}
