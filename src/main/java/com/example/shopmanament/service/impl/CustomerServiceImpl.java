package com.example.shopmanament.service.impl;

import com.example.shopmanament.dto.*;
import com.example.shopmanament.entity.Customer;
import com.example.shopmanament.entity.SellingOrder;
import com.example.shopmanament.exception.AppException;
import com.example.shopmanament.exception.Errors;
import com.example.shopmanament.repository.CustomerRepository;
import com.example.shopmanament.repository.SellingOrderRepository;
import com.example.shopmanament.service.CustomerService;
import com.example.shopmanament.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepo;
    private final ModelMapper mapper = new ModelMapper();
    private final UserService userService;
    private final SellingOrderRepository sellingOrderRepository;
    public Integer totalOrderByCustomer(Long customerId) {
        List<SellingOrder> entities = sellingOrderRepository.findByCustomerId(customerId);
        return entities.size();
    }
    public BigDecimal totalOrderValueByCustomer(Long customerId) {
        List<SellingOrder> entities = sellingOrderRepository.findByCustomerId(customerId);
        return entities.stream()
                .map(SellingOrder::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
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
    public BasePage<CustomerDTO> getAll(String searchString, Long storeId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize);

        Page<Customer> customers = customerRepo.getAll(searchString, storeId, pageable);
        BasePage<CustomerDTO> response = new BasePage<>();
        MetaData metaData =  new MetaData();
        metaData.setTotalElements(customers.getTotalElements());
        metaData.setTotalPages(customers.getTotalPages());
        metaData.setElements(customers.getNumberOfElements());
        response.setMetaData(metaData);
        response.setData(customers.get().map(customer -> {
            CustomerDTO dto = mapper.map(customer, CustomerDTO.class);
            dto.setTotalOrder(totalOrderByCustomer(customer.getId()));
            dto.setTotalMoney(totalOrderValueByCustomer(customer.getId()));
            return dto;
        }).collect(Collectors.toList()));
        return response;
    }
    public Date getDate(String time) {
        Calendar calendar = Calendar.getInstance();
        Date response = new Date(calendar.getTimeInMillis());

        switch (time) {
            case "TODAY":
                // fromTime is already set to today
                break;
            case "WEEK":
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                response = calendar.getTime();
                break;
            case "MONTH":
                calendar.add(Calendar.MONTH, -1);
                response = calendar.getTime();
                break;
            case "THREE_MONTH":
                calendar.add(Calendar.MONTH, -3);
                response = calendar.getTime();
                break;
        }

        return response;
    }
    @Override
    public List<CustomerStatisticDto> getTopCustomerExpenditure(Long storeId, String time, Long inventoryId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date current = new Date();
        String fromTime = dateFormat.format(getDate(time));
        String toTime = dateFormat.format(current);
        List<CustomerStatisticDto> response = new ArrayList<CustomerStatisticDto>();
        List<Object[]> queryResult = sellingOrderRepository.getTopCustomer(storeId,inventoryId, fromTime, toTime);
        if (queryResult == null || queryResult.isEmpty()) {
            // Handle empty results by creating an empty statistic for that day
            return response;
        } else { // Get the first (and likely only) result
            response = queryResult.stream().map(query -> {
                Long id  = (Long) query[0];
                if(id == 0 || id == null) {
                    CustomerDTO dto = new CustomerDTO();
                    return new CustomerStatisticDto(dto, (BigDecimal) query[1], (Long) query[2]);
                }
                return new CustomerStatisticDto(getDetail((Long) query[0]), (BigDecimal) query[1], (Long) query[2]);
            }).collect(Collectors.toList());
        }
        return response;
    }
    @Override
    public List<CustomerStatisticDto> getTopCustomerOrder(Long storeId, String time, Long inventoryId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date current = new Date();
        String fromTime = dateFormat.format(getDate(time));
        String toTime = dateFormat.format(current);
        List<CustomerStatisticDto> response = new ArrayList<CustomerStatisticDto>();
        List<Object[]> queryResult = sellingOrderRepository.getTopCustomerOrder(storeId,inventoryId, fromTime, toTime);
        if (queryResult == null || queryResult.isEmpty()) {
            // Handle empty results by creating an empty statistic for that day
            return response;
        } else {
            Object[] result = queryResult.get(0); // Get the first (and likely only) result
            response = queryResult.stream().map(query -> {
                Long id  = (Long) query[0];
                if(id == 0 || id == null) {
                    CustomerDTO dto = new CustomerDTO();
                    return new CustomerStatisticDto(dto, (BigDecimal) query[1], (Long) query[2]);
                }

                return new CustomerStatisticDto(getDetail((Long) query[0]), (BigDecimal) query[1], (Long) query[2]);
            }).collect(Collectors.toList());
        }
        return response;
    }

}
