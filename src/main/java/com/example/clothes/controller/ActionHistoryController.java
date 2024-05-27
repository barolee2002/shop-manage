package com.example.clothes.controller;

import com.example.clothes.dto.CookieDto;
import com.example.clothes.dto.Response;
import com.example.clothes.dto.request.ActionHistoryRequest;
import com.example.clothes.service.ActionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action-history")
@RequiredArgsConstructor
public class ActionHistoryController {
    private final ActionHistoryService acionHistoryService;
    @PostMapping("/add")
    public Response<String> creating(@CookieValue(value = "userInfo", required = false) CookieDto cookieDto, @RequestBody ActionHistoryRequest request) {
        return new Response<>(HttpStatus.OK.value(), acionHistoryService.create(cookieDto, request.getMessage()));
    }
}
