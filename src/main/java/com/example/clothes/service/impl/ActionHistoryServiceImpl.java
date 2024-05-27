package com.example.clothes.service.impl;

import com.example.clothes.dto.CookieDto;
import com.example.clothes.entity.ActionHistory;
import com.example.clothes.repository.ActionHistoryRepository;
import com.example.clothes.repository.UserRepository;
import com.example.clothes.service.ActionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

@RequiredArgsConstructor
public class ActionHistoryServiceImpl implements ActionHistoryService {
    private final ActionHistoryRepository actionHistoryRepository;
    private final UserRepository user;
    private Date current = new Date();
    @Override
    public String create(CookieDto dto, String message) {
        ActionHistory actionHistory = new ActionHistory();
        if(dto != null) {
            actionHistory.setUserId(dto.getUserId());
        }
        actionHistory.setMessage(message);
        actionHistory.setCreateAt(current);
        actionHistoryRepository.save(actionHistory);
        return "success";
    }
}
