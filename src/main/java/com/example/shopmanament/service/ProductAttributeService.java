package com.example.shopmanament.service;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.ProductAttributeDTO;
import com.example.shopmanament.dto.ProductInventoryManamentDto;
import com.example.shopmanament.dto.response.ProductAttributeResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductAttributeService {
    ProductAttributeDTO create(ProductAttributeDTO attributeDTO,Long storeId, Long productId);



    List<ProductAttributeDTO> getAllAttributeByProduct(Long productId);

    ProductAttributeDTO update(Long attributeId, ProductAttributeDTO attributeDTO);

    ProductAttributeDTO getDetail(Long attributeId);

    Long delete(Long attributeId);


    List<ProductAttributeResponseDto> getByProductAndInventory(Long productId, Long inventoryId, String searchString);

    BigDecimal minPriceByProduct(Long productId);

    BigDecimal maxPriceByProduct(Long productId);

    Integer totalQuantityByInventory(Long inventoryId, Long productId);

    BasePage<ProductInventoryManamentDto> getDetailManament(Long inventoryId, String searchString, String time,Integer minQuantity, Integer quantity, Integer page, Integer pageSize);
}
