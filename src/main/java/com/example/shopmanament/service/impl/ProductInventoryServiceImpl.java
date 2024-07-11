package com.example.shopmanament.service.impl;

import com.example.shopmanament.dto.ProductAttributeInventoryDTO;
import com.example.shopmanament.entity.Inventory;
import com.example.shopmanament.entity.ProductInventory;
import com.example.shopmanament.repository.InventoryRepository;
import com.example.shopmanament.repository.ProductInventoryRepository;
import com.example.shopmanament.repository.ProductRepository;
import com.example.shopmanament.service.InventoryService;
import com.example.shopmanament.service.ProductInventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInventoryServiceImpl implements ProductInventoryService {
    private final ProductInventoryRepository productInventoryRepository;
    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

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
    public String updateQuantity(Long inventoryId, Long productId, Integer quantity) {
        Date current = new Date();
        ProductInventory productInventory = productInventoryRepository.findByProductIdAndInventoryId(productId, inventoryId);
        if(productInventory != null) {
            productInventory.setQuantity(quantity);
            productInventory.setUpdateAt(current);
            productInventoryRepository.save(productInventory);
            return "success";
        }
        ProductInventory newProductInventory = new ProductInventory();
        newProductInventory.setInventoryId(inventoryId);
        newProductInventory.setProductId(productId);
        newProductInventory.setQuantity(quantity);
        newProductInventory.setUpdateAt(current);
        productInventoryRepository.save(newProductInventory);
        return "success";

    }
    @Override
    public Page<ProductInventory> inventoryProductManament(Long inventoryId, String searchString, String time,Integer minQuantity, Integer quantity, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<ProductInventory> productsPage = productInventoryRepository.inventoryMnament(inventoryId,searchString,time,minQuantity,quantity,pageable);
        return productsPage;
    }
    @Override
    @Transactional
    public Long createNewProduct(Long productId, Long storeId) {
        List<Inventory> inventories = inventoryRepository.findByStoreId(storeId);
        inventories.stream().forEach(inventory -> {
            ProductInventory entity = new ProductInventory();
            entity.setProductId(productId);
            entity.setInventoryId(inventory.getId());
            entity.setQuantity(0);
            productInventoryRepository.save(entity);
        });
        return productId;

    }

}
