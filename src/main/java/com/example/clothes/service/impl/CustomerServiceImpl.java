package com.example.clothes.service.impl;

import com.example.clothes.dto.CustomerDTO;
import com.example.clothes.entity.Customer;
import com.example.clothes.repository.CustomerRepository;
import com.example.clothes.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepo;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public CustomerDTO create(CustomerDTO customerDTO) {
        Customer customer = mapper.map(customerDTO, Customer.class);
        return mapper.map(customerRepo.save(customer), CustomerDTO.class);

    }
}
