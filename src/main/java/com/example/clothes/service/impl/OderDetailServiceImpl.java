package com.example.clothes.service.impl;

import com.example.clothes.dto.OrderDetailDTO;
import com.example.clothes.entity.OrderDetail;
import com.example.clothes.repository.OrderDetailRepository;
import com.example.clothes.service.OderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OderDetailServiceImpl implements OderDetailService {
    private final OrderDetailRepository orderDetailRepo;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public OrderDetailDTO create(OrderDetailDTO orderDetailDTO, Long orderId) {
        OrderDetail orderDetail = mapper.map(orderDetailDTO, OrderDetail.class);
        orderDetail.setOderId(orderId);
        return mapper.map(orderDetailRepo.save(orderDetail), OrderDetailDTO.class);
    }
}
