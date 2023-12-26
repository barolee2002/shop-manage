package com.example.clothes.service;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductAttributeDTO;

import java.util.List;

public interface ProductAttributeService {
    ProductAttributeDTO create(ProductAttributeDTO attributeDTO);

    BasePage<ProductAttributeDTO> getAllAttributeByProduct(Long productId, Integer page, Integer pageSize);

    ProductAttributeDTO update(Long attributeId, ProductAttributeDTO attributeDTO);

    Long delete(Long attributeId);
}
