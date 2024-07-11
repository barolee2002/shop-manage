package com.example.shopmanament.service;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.request.LoginRequest;
import com.example.shopmanament.dto.request.UserDTORequest;
import com.example.shopmanament.dto.response.LoginResponse;
import com.example.shopmanament.dto.response.UserDTOResponse;
import com.example.shopmanament.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDTOResponse addUser(UserDTORequest userDTO);



    //thiếu thêm nhân viên vào kho
    UserDTOResponse addStaff(UserDTORequest staff);

    LoginResponse login(LoginRequest loginRequest);

    UserDTOResponse update(UserDTORequest userDTO, Long userId);

    UserDTOResponse getInfo(Long userId);


    List<UserDTOResponse> getAll(Long storeId);

    Long logout(CookieDto cookieDto);

    BasePage<UserDTOResponse> getAllPage(Long storeId, String searchString, String role, Integer page, Integer pageSize);

    Integer sendConfirmationEmail(@NotNull User user);

    Integer confirmUser(String confirmationToken);

    void getWorkPoints(Long storeId, String time, HttpServletResponse response) throws IOException;
}
