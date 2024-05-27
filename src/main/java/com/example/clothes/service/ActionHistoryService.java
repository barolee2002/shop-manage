package com.example.clothes.service;

import com.example.clothes.dto.CookieDto;

public interface ActionHistoryService {
    String create(CookieDto dto, String message);
}
