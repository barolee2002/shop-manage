package com.example.clothes.service.impl;

import com.example.clothes.dto.OrderDetailDTO;
import com.example.clothes.dto.SellingOrderDTO;
import com.example.clothes.dto.response.UserDTOResponse;
import com.example.clothes.entity.SellingOrder;
import com.example.clothes.entity.User;
import com.example.clothes.repository.SellingOrderRepository;
import com.example.clothes.repository.UserRepository;
import com.example.clothes.service.OderDetailService;
import com.example.clothes.service.SellingOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellingOrderServiceImpl implements SellingOrderService {
    private final SellingOrderRepository sellingOrderRepo;
    private final OderDetailService detailService;
    private final UserRepository userRepo;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public SellingOrderDTO create(SellingOrderDTO orderDTO) {
        SellingOrder sellingOrder = mapper.map(orderDTO, SellingOrder.class);
        sellingOrder.setCustomerId(orderDTO.getCustomerDTO().getId());
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
}
