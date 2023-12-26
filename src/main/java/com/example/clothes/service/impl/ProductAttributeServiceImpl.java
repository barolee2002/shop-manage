package com.example.clothes.service.impl;

import com.example.clothes.dto.ProductAttributeDTO;
import com.example.clothes.entity.ProductAttribute;
import com.example.clothes.repository.ProductAttributeRepository;
import com.example.clothes.service.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeServiceImpl implements ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepo;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public ProductAttributeDTO create(ProductAttributeDTO attributeDTO) {
        ProductAttribute attribute = new ProductAttribute();
        attribute = mapper.map(attributeDTO, ProductAttribute.class);
        return mapper.map(productAttributeRepo.save(attribute), ProductAttributeDTO.class);

    }

    @Override
    public List<ProductAttributeDTO> getAllAttributeByProduct(Long productId) {
        List<ProductAttribute> attributes = productAttributeRepo.findByProductId(productId);
        return Arrays.asList(mapper.map(attributes, ProductAttributeDTO[].class));
    }
    @Override
    public ProductAttributeDTO update (Long attributeId, ProductAttributeDTO attributeDTO) {
        ProductAttribute attribute = productAttributeRepo.findById((attributeId)).get();
        attribute = mapper.map(attributeDTO, ProductAttribute.class);
        return mapper.map(productAttributeRepo.save(attribute), ProductAttributeDTO.class);

    }
}
