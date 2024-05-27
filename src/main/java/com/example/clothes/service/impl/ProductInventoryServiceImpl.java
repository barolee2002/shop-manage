package com.example.clothes.service.impl;

import com.example.clothes.dto.ProductAttributeInventoryDTO;
import com.example.clothes.entity.ProductInventory;
import com.example.clothes.repository.ProductInventoryRepository;
import com.example.clothes.service.InventoryService;
import com.example.clothes.service.ProductInventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInventoryServiceImpl implements ProductInventoryService {
    private final ProductInventoryRepository productInventoryRepository;
    private final InventoryService inventoryService;

    private final ModelMapper mapper = new ModelMapper();
    @Override
    public List<ProductAttributeInventoryDTO> getAllByProductId(Long productId) {
        List<ProductInventory> productInventories = productInventoryRepository.findByProductId(productId);
        List<ProductAttributeInventoryDTO> productAttributeInventoryDTOS = new ArrayList<ProductAttributeInventoryDTO>();
        productAttributeInventoryDTOS = productInventories.stream().map(item -> {
            ProductAttributeInventoryDTO dto = mapper.map(item, ProductAttributeInventoryDTO.class);
            dto.setInventory(inventoryService.getDetail(item.getInventoryId()));
            return dto;
        }).collect(Collectors.toList());
        return productAttributeInventoryDTOS;

    }
    @Override
    public ProductAttributeInventoryDTO create(ProductAttributeInventoryDTO dto) {
        ProductInventory productInventory = mapper.map(dto, ProductInventory.class);
        productInventory.setInventoryId(dto.getInventory().getId());
        System.out.println(dto);
        return mapper.map(productInventoryRepository.save(productInventory), ProductAttributeInventoryDTO.class);
    }
    @Override
    public ProductAttributeInventoryDTO update ( ProductAttributeInventoryDTO dto) {
        ProductInventory productInventory = productInventoryRepository.findById(dto.getId()).get();
        productInventory = mapper.map(dto, ProductInventory.class);
        return mapper.map(productInventoryRepository.save(productInventory), ProductAttributeInventoryDTO.class);
    }
    @Override
    public ProductAttributeInventoryDTO getByProductAndInventory(Long productId, Long inventoryId) {
        ProductInventory productInventory = productInventoryRepository.findByProductIdAndInventoryId(productId, inventoryId);
        if (productInventory == null) {return null;}
        return mapper.map(productInventory, ProductAttributeInventoryDTO.class);
    }
    @Override
    public String updateQuantity(Long inventoryId, Long productId, Integer quntity) {
        ProductInventory productInventory = productInventoryRepository.findByProductIdAndInventoryId(productId, inventoryId);
        if(productInventory != null) {
            productInventory.setQuantity(quntity);
            productInventoryRepository.save(productInventory);
            return "success";
        }
        ProductInventory newProductInventory = new ProductInventory();
        newProductInventory.setInventoryId(inventoryId);
        newProductInventory.setProductId(productId);
        newProductInventory.setQuantity(quntity);
        productInventoryRepository.save(newProductInventory);
        return "success";

    }


}
