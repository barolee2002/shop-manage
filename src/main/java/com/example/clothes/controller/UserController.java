package com.example.clothes.controller;

import com.example.clothes.dto.Response;
import com.example.clothes.dto.request.LoginRequest;
import com.example.clothes.dto.request.UserDTORequest;
import com.example.clothes.dto.response.LoginResponse;
import com.example.clothes.dto.response.StaffResponse;
import com.example.clothes.dto.response.UserDTOResponse;
import com.example.clothes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping(value = "/admin")
    public Response<UserDTOResponse> signup(@RequestBody UserDTOResponse userDTO) {
        return new Response<>(HttpStatus.CREATED.value(), userService.addUser(userDTO));
    }
    //create admin account
    @PostMapping(value = "/login")
    public Response<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new Response<>(HttpStatus.OK.value(), userService.login(loginRequest));
    }
    //create staff account
    @PostMapping(value = "/staff")
    public Response<StaffResponse> staff(
            @RequestBody StaffResponse staffRequest
    ) {
        return new Response<>(HttpStatus.OK.value(), userService.addStaff(staffRequest));
    }

    @PutMapping(value = "/{userId}")
    public Response<UserDTOResponse> update(@ModelAttribute UserDTORequest userDTO, @PathVariable Long userId) {
        return new Response<>(HttpStatus.OK.value(), userService.update(userDTO, userId));
    }
    @GetMapping( value = "/{userId}")
    public Response<UserDTOResponse> getInfo(@PathVariable Long userId){
        return new Response<>(HttpStatus.OK.value(), userService.getInfo(userId));
    }
}
