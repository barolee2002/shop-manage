package com.example.clothes.service.impl;

import com.example.clothes.dto.StockTakeDetailDto;
import com.example.clothes.entity.StockTakeDetail;
import com.example.clothes.exception.AppException;
import com.example.clothes.exception.Errors;
import com.example.clothes.repository.StockTakeDetailRepository;
import com.example.clothes.service.ProductAttributeService;
import com.example.clothes.service.StockTakeDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockTakeDetailServiceImpl implements StockTakeDetailService {
    private final StockTakeDetailRepository stockTakeDetailRepository;
    private final ProductAttributeService productAttributeService;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public StockTakeDetailDto createOrUpdate(StockTakeDetailDto request, Long stockTakeId) {
        StockTakeDetail entity ;
        if(stockTakeDetailRepository.findById(request.getId()).isEmpty()) {
            entity = mapper.map(request, StockTakeDetail.class);
        } else {
            entity = stockTakeDetailRepository.findById(request.getId()).get();
            entity = mapper.map(request, StockTakeDetail.class);
        }
        entity.setStockTake(stockTakeId);
        entity.setProductId(request.getProductAttribute().getId());
        stockTakeDetailRepository.save(entity);
        return mapper.map(entity,StockTakeDetailDto.class);
    }
    @Override
    public List<StockTakeDetailDto> getByStockTake(Long stockTake) {
        List<StockTakeDetail> entities = stockTakeDetailRepository.findByStockTake(stockTake);
        List<StockTakeDetailDto> response = entities.stream().map(entity -> {
            StockTakeDetailDto dto = mapper.map(entity, StockTakeDetailDto.class);
            dto.setProductAttribute(productAttributeService.getDetail(entity.getProductId()));
            dto.setProductAttribute(productAttributeService.getDetail(entity.getProductId()));
            return dto;
        }).collect(Collectors.toList());
        return response;
    }
    @Override
    public Long delete(Long id) {
        Optional<StockTakeDetail> detail = stockTakeDetailRepository.findById(id);
        if(detail.isEmpty()) {
            throw new AppException(Errors.NOT_FOUND);
        }
        stockTakeDetailRepository.delete(detail.get());
        return id;
    }
}
