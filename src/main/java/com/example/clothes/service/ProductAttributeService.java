package com.example.clothes.service;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductAttributeDTO;
import com.example.clothes.dto.response.ProductAttributeResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductAttributeService {
    ProductAttributeDTO create(ProductAttributeDTO attributeDTO, Long productId);



    List<ProductAttributeDTO> getAllAttributeByProduct(Long productId);

    ProductAttributeDTO update(Long attributeId, ProductAttributeDTO attributeDTO);

    ProductAttributeDTO getDetail(Long attributeId);

    Long delete(Long attributeId);


    List<ProductAttributeResponseDto> getByProductAndInventory(Long productId, Long inventoryId, String searchString);

    BigDecimal minPriceByProductAndInventory(Long productId, Long inventoryId);

    BigDecimal maxPriceByProductAndInventory(Long productId, Long inventoryId);

    Integer totalQuantityByInventory(Long inventoryId, Long productId);
}
