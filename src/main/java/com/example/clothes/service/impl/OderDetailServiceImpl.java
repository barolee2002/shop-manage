package com.example.clothes.service.impl;

import com.example.clothes.dto.OrderDetailDTO;
import com.example.clothes.dto.ProductAttributeDTO;
import com.example.clothes.entity.OrderDetail;
import com.example.clothes.repository.OrderDetailRepository;
import com.example.clothes.service.OderDetailService;
import com.example.clothes.service.ProductAttributeService;
import com.example.clothes.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OderDetailServiceImpl implements OderDetailService {
    private final OrderDetailRepository orderDetailRepo;
    private final ProductService productService;
    private final ProductAttributeService productAttributeService;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public OrderDetailDTO create(OrderDetailDTO orderDetailDTO, Long orderId) {
        OrderDetail orderDetail = mapper.map(orderDetailDTO, OrderDetail.class);
        orderDetail.setProductAttributeId(orderDetailDTO.getProductAttribute().getId());
        orderDetail.setOrderId(orderId);
        return mapper.map(orderDetailRepo.save(orderDetail), OrderDetailDTO.class);
    }
    @Override
    public List<OrderDetailDTO> getByOrderId(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId(orderId);
        List<OrderDetailDTO> response = orderDetails.stream().map(orderDetail -> {
            OrderDetailDTO dto = mapper.map(orderDetail, OrderDetailDTO.class);
            ProductAttributeDTO attributeDTO = productAttributeService.getDetail(orderDetail.getProductAttributeId());
            dto.setProductAttribute(attributeDTO);
            dto.setProduct(productService.getDetail(attributeDTO.getProductId()));
            return dto;
        }).collect(Collectors.toList());
        return response;
    }
}
