package com.example.clothes.controller;

import com.example.clothes.dto.Response;
import com.example.clothes.dto.SellingOrderDTO;
import com.example.clothes.service.SellingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/selling-order")
@RequiredArgsConstructor
public class SellingOrderController {
    private final SellingOrderService sellingOrderService;
    @PostMapping("/creating")
    public Response<SellingOrderDTO> create(@RequestBody SellingOrderDTO sellingOrderDTO) {
        return new Response<>(HttpStatus.OK.value(), sellingOrderService.create(sellingOrderDTO));
    }
    @GetMapping("/list-all/{storeId}")
    public Response<List<SellingOrderDTO>> getAll (@PathVariable Long storeId) {
        return new Response<>(HttpStatus.OK.value(), sellingOrderService.getAll(storeId));
    }
    @GetMapping("/get-detail/{orderId}")
    public Response<SellingOrderDTO> getDetail (@PathVariable Long orderId) {
        return new Response<>(HttpStatus.OK.value(), sellingOrderService.getDetail(orderId));
    }
}
