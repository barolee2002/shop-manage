package com.example.shopmanament.service.impl;

import com.example.shopmanament.dto.*;
import com.example.shopmanament.entity.ActionHistory;
import com.example.shopmanament.repository.ActionHistoryRepository;
import com.example.shopmanament.repository.UserRepository;
import com.example.shopmanament.service.ActionHistoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class ActionHistoryServiceImpl implements ActionHistoryService {
    private final ActionHistoryRepository actionHistoryRepository;
    private final UserRepository user;
    private final ModelMapper mapper= new ModelMapper();
    @Override
    public String create(CookieDto dto, String message) {
        ActionHistory actionHistory = new ActionHistory();
        Date current = new Date();
        if(dto != null) {
            actionHistory.setUserId(dto.getUserId());
        }
        actionHistory.setMessage(message);
        actionHistory.setCreateAt(current);
        actionHistoryRepository.save(actionHistory);
        return "success";
    }
    @Override
    public List<ActionHistoryDto> getByUser(Long userId) {
        List<ActionHistory> entities = actionHistoryRepository.getByUser(userId);
        List<ActionHistoryDto> response = Arrays.asList(mapper.map(entities, ActionHistoryDto[].class));
        return response;
    }

    public List<java.sql.Date> getDateArray(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date fromTime = new Date(calendar.getTimeInMillis());
        Date toTime = new Date(System.currentTimeMillis());

        switch (time) {
            case "MONTH":
                calendar.add(Calendar.MONTH, -1);
                fromTime = calendar.getTime();
                break;
            case "THREE_MONTH":
                calendar.add(Calendar.MONTH, -3);
                fromTime = calendar.getTime();
                break;
        }

        List<java.sql.Date> dateList = new ArrayList<>();
        calendar.setTime(fromTime);
        while (!calendar.getTime().after(toTime)) {
            dateList.add(new java.sql.Date(calendar.getTimeInMillis()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return dateList;
    }

    @Override
    public List<WorkPointDto> getWorkPointsUser(Long userId, String time) {
        List<java.sql.Date> arrayDate = getDateArray(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateResponseFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<WorkPointDto> response = arrayDate.stream().map(date -> {
            // Zero out the time part
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date formattedDate = cal.getTime();

            String day = dateFormat.format(formattedDate);
            List<Object[]> queryResponse = actionHistoryRepository.getWorkPoint(day,userId);

            if (queryResponse == null || queryResponse.isEmpty()) {
                return new WorkPointDto(dateResponseFormat.format(date), "", "");
            } else {
                Object[] result = queryResponse.get(0);
                Date dayRes = (Date) result[0];


                String loginString = "";
                String logoutString = "";

                if ((Date) result[1] != null) {loginString = timeFormat.format((Date) result[1]);}
                if ((Date) result[2] != null) {logoutString = timeFormat.format((Date) result[2]);}

                return new WorkPointDto(dateResponseFormat.format(date), loginString,logoutString);
            }
        }).collect(Collectors.toList());

        return response;
    }

}
