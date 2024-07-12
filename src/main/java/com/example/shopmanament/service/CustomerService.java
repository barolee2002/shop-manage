package com.example.shopmanament.service;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.CustomerDTO;
import com.example.shopmanament.dto.CustomerStatisticDto;

import java.util.List;

public interface CustomerService {
    CustomerDTO create(CustomerDTO customerDTO);

    List<CustomerDTO> getList(Long storeId);

    CustomerDTO getDetail(Long customerId);

    CustomerDTO update(CustomerDTO customerDTO);


    BasePage<CustomerDTO> getAll(String searchString,Long storeId, Integer page, Integer pageSize);

    List<CustomerStatisticDto> getTopCustomerExpenditure(Long storeId, String time, Long inventoryId);

    List<CustomerStatisticDto> getTopCustomerOrder(Long storeId, String time, Long inventoryId);
}
