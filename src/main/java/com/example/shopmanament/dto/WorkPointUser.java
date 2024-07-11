package com.example.shopmanament.dto;

import com.example.shopmanament.dto.response.UserDTOResponse;
import com.example.shopmanament.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WorkPointUser {
    private UserDTOResponse user;
    private List<WorkPointDto> workPointDtoList;
    public WorkPointUser(UserDTOResponse user, List<WorkPointDto> workPointDtoList) {
        this.user = user;
        this.workPointDtoList = workPointDtoList;
    }

}
