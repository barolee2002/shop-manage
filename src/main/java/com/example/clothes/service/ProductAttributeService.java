package com.example.clothes.service;

import com.example.clothes.dto.ProductAttributeDTO;

import java.util.List;

public interface ProductAttributeService {
    ProductAttributeDTO create(ProductAttributeDTO attributeDTO);

    List<ProductAttributeDTO> getAllAttributeByProduct(Long productId);



    ProductAttributeDTO update(Long attributeId, ProductAttributeDTO attributeDTO);
}
