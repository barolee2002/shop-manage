package com.example.clothes.service.impl;

import com.example.clothes.dto.OrderDetailDTO;
import com.example.clothes.dto.SellingOrderDTO;
import com.example.clothes.dto.response.UserDTOResponse;
import com.example.clothes.entity.SellingOrder;
import com.example.clothes.entity.User;
import com.example.clothes.repository.SellingOrderRepository;
import com.example.clothes.repository.UserRepository;
import com.example.clothes.service.CustomerService;
import com.example.clothes.service.OderDetailService;
import com.example.clothes.service.SellingOrderService;
import com.example.clothes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellingOrderServiceImpl implements SellingOrderService {
    private final SellingOrderRepository sellingOrderRepo;
    private final OderDetailService detailService;
    private final CustomerService customerService;
    private final UserService userService;
    private final UserRepository userRepo;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public SellingOrderDTO create(SellingOrderDTO orderDTO) {
        SellingOrder sellingOrder = mapper.map(orderDTO, SellingOrder.class);
        sellingOrder.setCustomerId(orderDTO.getCustomerDTO().getId());
        if(orderDTO.getCode() == null) {
            sellingOrder.setCode("OD" + String.format("%8d", sellingOrderRepo.countByStoreId(orderDTO.getId()) + 1));
        }
        sellingOrderRepo.save(sellingOrder);
        List<OrderDetailDTO> details =orderDTO.getDetailDTOS().stream().map(item -> {
            return detailService.create(item, sellingOrder.getId());
        }).collect(Collectors.toList());
        SellingOrderDTO sellingOrderDTO = new SellingOrderDTO();
        sellingOrderDTO = mapper.map(sellingOrder,SellingOrderDTO.class);
        sellingOrderDTO.setDetailDTOS(details);
        User staff = new User();
        staff = userRepo.findById(sellingOrder.getUserId()).get();
        sellingOrderDTO.setStaff(mapper.map(staff, UserDTOResponse.class));
        return sellingOrderDTO;
    }

    @Override
    public List<SellingOrderDTO> getAll(Long storeId)  {
        List<SellingOrder> sellingOrders = sellingOrderRepo.findByStoreId(storeId);
        List<SellingOrderDTO> response = sellingOrders.stream().map(sellingOrder -> {
            SellingOrderDTO dto = mapper.map(sellingOrder, SellingOrderDTO.class);
            dto.setCustomerDTO(customerService.getDetail(sellingOrder.getCustomerId()));
            dto.setStaff(userService.getInfo(sellingOrder.getUserId()));
            return dto;
        }).collect(Collectors.toList());
        return response;
    }
    @Override
    public SellingOrderDTO getDetail(Long orderId) {
        Optional<SellingOrder> sellingOrder = sellingOrderRepo.findById(orderId);
        SellingOrderDTO response = mapper.map(sellingOrder, SellingOrderDTO.class);
        response.setStaff(userService.getInfo(sellingOrder.get().getUserId()));
        response.setCustomerDTO(customerService.getDetail(sellingOrder.get().getCustomerId()));
        response.setDetailDTOS(detailService.getByOrderId(orderId));
        return response;
    }
}
