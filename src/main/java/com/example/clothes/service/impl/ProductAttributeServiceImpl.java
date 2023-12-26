package com.example.clothes.service.impl;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductAttributeDTO;
import com.example.clothes.entity.ProductAttribute;
import com.example.clothes.repository.ProductAttributeRepository;
import com.example.clothes.service.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAttributeServiceImpl implements ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepo;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public ProductAttributeDTO create(ProductAttributeDTO attributeDTO) {
        ProductAttribute attribute = new ProductAttribute();
        attribute = mapper.map(attributeDTO, ProductAttribute.class);
        attribute.setStatus(1);
        return mapper.map(productAttributeRepo.save(attribute), ProductAttributeDTO.class);

    }

    @Override
    public BasePage<ProductAttributeDTO> getAllAttributeByProduct(Long productId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<ProductAttribute> productPage = productAttributeRepo.findByProductIdAndStatus(productId,1, pageable);
        BasePage<ProductAttributeDTO> dataPage = new BasePage<>();
        dataPage.setTotalElements(productPage.getTotalElements());
        dataPage.setTotalPages(productPage.getTotalPages());
        dataPage.setElements(productPage.getNumberOfElements());
        dataPage.setData(productPage.get().map(attribute -> mapper.map(attribute, ProductAttributeDTO.class)).toList());
        return dataPage;
    }
    @Override
    public ProductAttributeDTO update (Long attributeId, ProductAttributeDTO attributeDTO) {
        ProductAttribute attribute = productAttributeRepo.findById((attributeId)).get();
        attribute = mapper.map(attributeDTO, ProductAttribute.class);
        return mapper.map(productAttributeRepo.save(attribute), ProductAttributeDTO.class);

    }
    @Override
    public Long delete(Long attributeId) {
        ProductAttribute attribute = productAttributeRepo.findById((attributeId)).get();
        attribute.setStatus(0);
        productAttributeRepo.save(attribute);
        return attribute.getId();
    }
}
