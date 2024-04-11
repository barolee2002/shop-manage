package com.example.clothes.service;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO create(CustomerDTO customerDTO);

    CustomerDTO getDetail(Long customerId);

    CustomerDTO update(CustomerDTO customerDTO);


    BasePage<CustomerDTO> getAll(Long storeId, Integer page, Integer pageSize);
}
