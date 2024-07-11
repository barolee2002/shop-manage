package com.example.shopmanament.service.impl;

import com.example.shopmanament.dto.OrderDetailDTO;
import com.example.shopmanament.dto.ProductAttributeDTO;
import com.example.shopmanament.entity.OrderDetail;
import com.example.shopmanament.repository.OrderDetailRepository;
import com.example.shopmanament.service.OderDetailService;
import com.example.shopmanament.service.ProductAttributeService;
import com.example.shopmanament.service.ProductInventoryService;
import com.example.shopmanament.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OderDetailServiceImpl implements OderDetailService {
    private final OrderDetailRepository orderDetailRepo;
    private final ProductService productService;
    private final ProductAttributeService productAttributeService;
    private final ProductInventoryService productInventoryService;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public BigDecimal totalOrderValueByOrder(Long orderId, Long inventoryId) {
        List<OrderDetail> entities = orderDetailRepo.findByOrderId(orderId);
        BigDecimal response = entities.stream().map(entity -> {
            ProductAttributeDTO priceEntity = productAttributeService.getDetail(entity.getProductAttributeId());
            return priceEntity.getSellPrice().multiply(BigDecimal.valueOf(entity.getQuantity()*((100-entity.getDiscount())/100)));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        return response;
    }
    @Override
    public BigDecimal totalOrderMoney(Long orderId, Long inventoryId) {
        List<OrderDetail> entities = orderDetailRepo.findByOrderId(orderId);
        BigDecimal response = entities.stream().map(entity -> {
            ProductAttributeDTO priceEntity = productAttributeService.getDetail(entity.getProductAttributeId());
            return priceEntity.getCostPrice().multiply(BigDecimal.valueOf(entity.getQuantity()*((100-entity.getDiscount())/100)));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        return response;
    }
    @Override
    @Transactional
    public OrderDetailDTO create(OrderDetailDTO orderDetailDTO, Long orderId) {
        OrderDetail orderDetail = mapper.map(orderDetailDTO, OrderDetail.class);
        orderDetail.setProductAttributeId(orderDetailDTO.getProduct().getId());
        orderDetail.setOrderId(orderId);
        OrderDetailDTO response = mapper.map(orderDetailRepo.save(orderDetail), OrderDetailDTO.class);
        return response;
    }
    @Override
    public List<OrderDetailDTO> getByOrderId(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId(orderId);
        List<OrderDetailDTO> response = orderDetails.stream().map(orderDetail -> {
            OrderDetailDTO dto = mapper.map(orderDetail, OrderDetailDTO.class);
            ProductAttributeDTO attributeDTO = productAttributeService.getDetail(orderDetail.getProductAttributeId());
            dto.setProduct(attributeDTO);
            return dto;
        }).collect(Collectors.toList());
        return response;
    }
}
