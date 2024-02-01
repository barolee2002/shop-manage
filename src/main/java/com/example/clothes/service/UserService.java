package com.example.clothes.service;

import com.example.clothes.dto.request.LoginRequest;
import com.example.clothes.dto.request.UserDTORequest;
import com.example.clothes.dto.response.LoginResponse;
import com.example.clothes.dto.response.StaffResponse;
import com.example.clothes.dto.response.UserDTOResponse;

public interface UserService {
    UserDTOResponse addUser(UserDTOResponse userDTO);



    //thiếu thêm nhân viên vào kho
    StaffResponse addStaff(StaffResponse staff);

    LoginResponse login(LoginRequest loginRequest);

    UserDTOResponse update(UserDTORequest userDTO, Long userId);

    UserDTOResponse getInfo(Long userId);


}
