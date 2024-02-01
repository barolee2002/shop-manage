package com.example.clothes.controller;

import com.example.clothes.dto.Response;
import com.example.clothes.dto.SellingOrderDTO;
import com.example.clothes.service.SellingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/selling-order")
@RequiredArgsConstructor
public class SellingOrderController {
    private final SellingOrderService sellingOrderService;
    @PostMapping("/creating")
    public Response<SellingOrderDTO> create(@RequestBody SellingOrderDTO sellingOrderDTO) {
        return new Response<>(HttpStatus.OK.value(), sellingOrderService.create(sellingOrderDTO));
    }
}
