package com.example.clothes.service;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductAttributeDTO;

import java.util.List;

public interface ProductAttributeService {
    ProductAttributeDTO create(ProductAttributeDTO attributeDTO);



    List<ProductAttributeDTO> getAllAttributeByProduct(Long productId);

    ProductAttributeDTO update(Long attributeId, ProductAttributeDTO attributeDTO);

    ProductAttributeDTO getDetail(Long attributeId);

    Long delete(Long attributeId);

    List<String> getMaterialByProductId(Long productId);

    List<String> getSizeByProductId(Long productId);

    List<String> getVarialtionByProductId(Long productId);

    List<String> getOriginByProductId(Long productId);
}
