package com.example.clothes.service.impl;

import com.example.clothes.dto.ProductAttributeDTO;
import com.example.clothes.entity.ProductAttribute;
import com.example.clothes.exception.AppException;
import com.example.clothes.exception.Errors;
import com.example.clothes.repository.ProductAttributeRepository;
import com.example.clothes.service.ProductAttributeService;
import com.example.clothes.service.ProductInventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductAttributeServiceImpl implements ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepo;
    private final ProductInventoryService productInventoryService;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public ProductAttributeDTO create(ProductAttributeDTO attributeDTO) {
        if(productAttributeRepo.findDupplicateAttribute(attributeDTO.getProductId(), attributeDTO.getSize(), attributeDTO.getMaterial(), attributeDTO.getVariation(), attributeDTO.getOrigin()) != null) {
            throw new AppException(Errors.EXIST_ATTRIBUTE);
        }
        ProductAttribute attribute = new ProductAttribute();
        attribute = mapper.map(attributeDTO, ProductAttribute.class);
        attribute.setStatus(1);
        return mapper.map(productAttributeRepo.save(attribute), ProductAttributeDTO.class);

    }

    @Override
    public List<ProductAttributeDTO> getAllAttributeByProduct(Long productId) {
        List<ProductAttributeDTO> productAttributeDTOS = new ArrayList<ProductAttributeDTO>();
        List<ProductAttribute> attributes = productAttributeRepo.findByProductIdAndStatus(productId,1);
         productAttributeDTOS = attributes.stream().map(attribute -> mapper.map(attribute, ProductAttributeDTO.class)).collect(Collectors.toList());
        productAttributeDTOS = productAttributeDTOS.stream().map(productAttributeDTO -> {
             productAttributeDTO.setInventoryList(productInventoryService.getAllByProductId(productAttributeDTO.getId()));
             return productAttributeDTO;
         }).collect(Collectors.toList());
        return productAttributeDTOS;
    }
    @Override
    public ProductAttributeDTO update (Long attributeId, ProductAttributeDTO attributeDTO) {
        ProductAttribute attribute = productAttributeRepo.findById((attributeId)).get();
        attribute = mapper.map(attributeDTO, ProductAttribute.class);
        return mapper.map(productAttributeRepo.save(attribute), ProductAttributeDTO.class);

    }
    @Override
    public ProductAttributeDTO getDetail( Long attributeId) {
        ProductAttribute attribute = productAttributeRepo.findById((attributeId)).get();
        ProductAttributeDTO dto = mapper.map(attribute, ProductAttributeDTO.class);
        dto.setInventoryList(productInventoryService.getAllByProductId(attributeId));
        return dto;
    }
    @Override
    public Long delete(Long attributeId) {
        ProductAttribute attribute = productAttributeRepo.findById((attributeId)).get();
        attribute.setStatus(0);
        productAttributeRepo.save(attribute);
        return attribute.getId();
    }
    @Override
    public List<String> getSizeByProductId(Long productId) {
        return productAttributeRepo.findSizeByProductId(productId);
    }
    @Override
    public List<String> getMaterialByProductId(Long productId) {
        return productAttributeRepo.findMaterialByProductId(productId);
    }
    @Override
    public List<String> getVarialtionByProductId(Long productId) {
        return productAttributeRepo.findColorByProductId(productId);
    }
    @Override
    public List<String> getOriginByProductId(Long productId) {
        return productAttributeRepo.findOriginByProductId(productId);
    }

}
