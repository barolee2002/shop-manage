package com.example.shopmanament.service;

import com.example.shopmanament.dto.ProductAttributeInventoryDTO;
import com.example.shopmanament.entity.ProductInventory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductInventoryService {
    List<ProductAttributeInventoryDTO> getAllByProductId(Long productId);

    ProductAttributeInventoryDTO create(ProductAttributeInventoryDTO dto);

    ProductAttributeInventoryDTO update(ProductAttributeInventoryDTO dto);

    ProductAttributeInventoryDTO getByProductAndInventory(Long productId, Long inventoryId);

    String updateQuantity(Long inventoryId, Long productId, Integer quntity);


    Page<ProductInventory> inventoryProductManament(Long inventoryId, String searchString, String time,Integer minQuantity, Integer quantity, Integer page, Integer pageSize);

    Long createNewProduct(Long productId, Long storeId);
}
