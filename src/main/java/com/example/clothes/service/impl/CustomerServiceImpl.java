package com.example.clothes.service.impl;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.CustomerDTO;
import com.example.clothes.dto.MetaData;
import com.example.clothes.entity.Customer;
import com.example.clothes.exception.AppException;
import com.example.clothes.exception.Errors;
import com.example.clothes.repository.CustomerRepository;
import com.example.clothes.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

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
    @Override
    public CustomerDTO getDetail(Long customerId) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        if(customer.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        return mapper.map(customer.get(), CustomerDTO.class);
    }
    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer customer = customerRepo.findById(customerDTO.getId()).get();
        customer = mapper.map(customerDTO, Customer.class);
        return mapper.map(customerRepo.save(customer), CustomerDTO.class);
    }
    @Override
    public BasePage<CustomerDTO> getAll(Long storeId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize);

        Page<Customer> customers = customerRepo.findByStoreId(storeId, pageable);
        BasePage<CustomerDTO> response = new BasePage<>();
        MetaData metaData =  new MetaData();
        metaData.setTotalElements(customers.getTotalElements());
        metaData.setTotalPages(customers.getTotalPages());
        metaData.setElements(customers.getNumberOfElements());
        response.setMetaData(metaData);
        response.setData(customers.get().map(customer -> mapper.map(customer, CustomerDTO.class)).collect(Collectors.toList()));
        return response;
    }

}
