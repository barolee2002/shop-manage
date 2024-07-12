package com.example.shopmanament.controller;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.CustomerDTO;
import com.example.shopmanament.dto.CustomerStatisticDto;
import com.example.shopmanament.dto.Response;
import com.example.shopmanament.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/list-all/{storeId}")
    public Response<BasePage<CustomerDTO>> getAll (
            @PathVariable Long storeId,
            @RequestParam( required = false) String searchString,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        return new Response<>(HttpStatus.OK.value(),customerService.getAll(searchString,storeId, page, pageSize));
    }
    @GetMapping("/list/{storeId}")
    public Response<List<CustomerDTO>> getList (
            @PathVariable Long storeId
    ) {
        return new Response<>(HttpStatus.OK.value(),customerService.getList(storeId));
    }
    @GetMapping("/list-top-expenditure/{storeId}")
    public Response<List<CustomerStatisticDto>> getTopCustomerExpenditure (
            @PathVariable Long storeId,
            @RequestParam( required = false, defaultValue = "WEEK") String time,
            @RequestParam(required = false) Long inventoryId
    ) {
        return new Response<>(HttpStatus.OK.value(),customerService.getTopCustomerExpenditure(storeId, time, inventoryId));
    }
    @GetMapping("/list-top-order/{storeId}")
    public Response<List<CustomerStatisticDto>> getTopCustomerOrder (
            @PathVariable Long storeId,
            @RequestParam( required = false, defaultValue = "WEEK") String time,
            @RequestParam(required = false) Long inventoryId
    ) {
        return new Response<>(HttpStatus.OK.value(),customerService.getTopCustomerOrder(storeId, time, inventoryId));
    }

}
