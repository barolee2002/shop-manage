package com.example.clothes.service;

import com.example.clothes.dto.request.LoginRequest;
import com.example.clothes.dto.request.UserDTORequest;
import com.example.clothes.dto.response.LoginResponse;
import com.example.clothes.dto.response.UserDTOResponse;

import java.util.List;

public interface UserService {
    UserDTOResponse addUser(UserDTORequest userDTO);



    //thiếu thêm nhân viên vào kho
    UserDTOResponse addStaff(UserDTORequest staff);

    LoginResponse login(LoginRequest loginRequest);

    UserDTOResponse update(UserDTORequest userDTO, Long userId);

    UserDTOResponse getInfo(Long userId);


    List<UserDTOResponse> getAll(Long storeId);
}
