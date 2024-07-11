package com.example.shopmanament.service;

import com.example.shopmanament.dto.ActionHistoryDto;
import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.WorkPointDto;

import java.util.List;

public interface ActionHistoryService {
    String create(CookieDto dto, String message);

    List<ActionHistoryDto> getByUser(Long userId);

    List<WorkPointDto> getWorkPointsUser(Long userId, String time);
}
