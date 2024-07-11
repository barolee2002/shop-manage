package com.example.shopmanament.controller;

import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.Response;
import com.example.shopmanament.dto.request.ActionHistoryRequest;
import com.example.shopmanament.service.ActionHistoryService;
import com.example.shopmanament.utils.JsonParse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action-history")
@RequiredArgsConstructor
public class ActionHistoryController {
    private final JsonParse jsonParse = new JsonParse();
    private final ActionHistoryService acionHistoryService;
    @PostMapping("/add")
    public Response<String> creating(@CookieValue(name = "userInfo", required = false) String cookieDto, @RequestBody ActionHistoryRequest request) {

        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(), acionHistoryService.create(dto, request.getMessage()));
    }
}
