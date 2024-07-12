package com.example.shopmanament.controller;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.Response;
import com.example.shopmanament.dto.request.LoginRequest;
import com.example.shopmanament.dto.request.UserDTORequest;
import com.example.shopmanament.dto.response.LoginResponse;
import com.example.shopmanament.dto.response.UserDTOResponse;
import com.example.shopmanament.service.UserService;
import com.example.shopmanament.utils.JsonParse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JsonParse jsonParse = new JsonParse();

    @PostMapping(value = "/signup")
    public Response<UserDTOResponse> signup(@RequestBody UserDTORequest userDTO) {
        return new Response<>(HttpStatus.CREATED.value(), userService.addUser(userDTO));
    }
    //create admin account
    @PostMapping(value = "/login")
    public Response<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new Response<>(HttpStatus.OK.value(), userService.login(loginRequest));
    }
    //create staff account
    @PostMapping(value = "/staff")
    public Response<UserDTOResponse> staff(
            @RequestBody UserDTORequest staffRequest
    ) {
        return new Response<>(HttpStatus.OK.value(), userService.addStaff(staffRequest));
    }

    @PutMapping(value = "/updating/{userId}")
    public Response<UserDTOResponse> update(@RequestBody UserDTORequest userDTO, @PathVariable Long userId) {
        return new Response<>(HttpStatus.OK.value(), userService.update(userDTO, userId));
    }
    @GetMapping( value = "/{userId}")
    public Response<UserDTOResponse> getInfo(@PathVariable Long userId){
        UserDTOResponse response = userService.getInfo(userId);
        return new Response<>(HttpStatus.OK.value(), response);
    }
    @GetMapping("/get-all/{storeId}")
    public Response<List<UserDTOResponse>> getAll(@PathVariable Long storeId) {
        return new Response<>(HttpStatus.OK.value(), userService.getAll(storeId));
    }
    @PostMapping("/logout")
    public Response<Long> logout(@CookieValue(value = "userInfo", required = false) String cookieDto) {
        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(), userService.logout(dto));
    }
    @GetMapping("/get-page/{storeId}")
    public Response<BasePage<UserDTOResponse>> getAllPage(
            @PathVariable Long storeId,
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) String role,
            @RequestParam( required = false, defaultValue = "1") Integer page,
            @RequestParam( required = false, defaultValue = "10") Integer pageSize

            ) {
        return new Response<>(HttpStatus.OK.value(), userService.getAllPage(storeId,searchString, role, page, pageSize));
    }
    @GetMapping("/confirm-account")
    public Response<Integer> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return new Response<>(HttpStatus.OK.value(), userService.confirmUser(confirmationToken));

    }
    @GetMapping("/export-work-points")
    public void exportWorkPoints(@RequestParam Long storeId, @RequestParam String time, HttpServletResponse response) {
        try {
            userService.getWorkPoints(storeId, time, response);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete/{userId}")
    public Response<String> delete(@PathVariable("userId") Long userId) {
        return new Response<>(HttpStatus.OK.value(), userService.delete(userId));
    }


}
