package com.example.clothes.controller;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.CustomerDTO;
import com.example.clothes.dto.Response;
import com.example.clothes.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping("/creating")
    public Response<CustomerDTO> create (@RequestBody CustomerDTO customerDTO) {
        return new Response<>(HttpStatus.OK.value(), customerService.create(customerDTO));
    }
    @GetMapping("/get-detail/{customerId}")
    public Response<CustomerDTO> getDetail (@PathVariable Long customerId) {
        return new Response<>(HttpStatus.OK.value(), customerService.getDetail(customerId));

    }
    @PutMapping("/updating")
    public Response<CustomerDTO> update (@RequestBody CustomerDTO customerDTO) {
        return new Response<>(HttpStatus.OK.value(), customerService.update(customerDTO));
    }
    @GetMapping("list-all/{storeId}")
    public Response<BasePage<CustomerDTO>> getAll (
            @PathVariable Long storeId,
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize
    ) {
        return new Response<>(HttpStatus.OK.value(),customerService.getAll(storeId, page, pageSize));
    }

}
