package com.example.clothes.service.impl;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductAttributeDTO;
import com.example.clothes.dto.ProductDTO;
import com.example.clothes.dto.response.ProductResponseDto;
import com.example.clothes.entity.Product;
import com.example.clothes.entity.User;
import com.example.clothes.repository.ProductRepository;
import com.example.clothes.repository.UserRepository;
import com.example.clothes.service.ProductAttributeService;
import com.example.clothes.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceimpl implements ProductService {
    private final ProductRepository productRepo;
    private final ModelMapper mapper = new ModelMapper();
    private final ProductAttributeService attributeService;
    private final UserRepository userRepo;
    @Override
    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        Product product =  mapper.map(productDTO, Product.class);
        product.setStatus(1);
        product.setCreateAt(new Date());
        product = productRepo.save(product);
        Product finalProduct = product;

        List<ProductAttributeDTO> attributeDTOS =  productDTO.getAttributes().stream().map((attribute) ->  attributeService.create(attribute, finalProduct.getId())).collect(Collectors.toList());
        ProductDTO response = mapper.map(product, ProductDTO.class);
        response.setAttributes(attributeDTOS);
        return response;
    }
    @Override
    @Transactional
    public ProductDTO update(ProductDTO productDTO, Long productId) {
        Product product = productRepo.findById(productId).get();
        product = mapper.map(productDTO, Product.class);
        product = productRepo.save(product);
        return getDetail(product.getId());
    }
    @Override
    public ProductDTO getDetail(Long productId) {
        Product product = productRepo.findById((productId)).get();
        ProductDTO productDTO = mapper.map(product,ProductDTO.class);
        productDTO.setAttributes(attributeService.getAllAttributeByProduct(productId));
        return productDTO;
    }
    @Override
    public BasePage<ProductResponseDto> get(String name, Long inventoryId, String category, Long userId, String fromTime, String toTime, Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        User user = userRepo.findById(userId).get();
        Page<Product> productPage = productRepo.findAll( name, category,user.getStoreId(),fromTime, toTime, pageable);
        List<ProductResponseDto> dtos = productPage.get().map(product -> mapper.map(product, ProductResponseDto.class)).toList();
        dtos = dtos.stream().map(dto -> {
            dto.setAttributes(attributeService.getByProductAndInventory(dto.getId(), inventoryId));
            dto.setMinPrice(attributeService.minPriceByProductAndInventory(dto.getId(),inventoryId));
            dto.setMaxPrice(attributeService.maxPriceByProductAndInventory(dto.getId(),inventoryId));
            dto.setTotalQuantity(attributeService.totalQuantityByInventory(dto.getId(),inventoryId));
            return dto;
        }).collect(Collectors.toList());
        BasePage<ProductResponseDto> dataPage = new BasePage<>();
        dataPage.setTotalElements(productPage.getTotalElements());
        dataPage.setTotalPages(productPage.getTotalPages());
        dataPage.setElements(productPage.getNumberOfElements());
        dataPage.setData(dtos);
        return dataPage;

    }
    @Override
    public List<String> getAllCatgories(Long userId) {
        User user = userRepo.findById(userId).get();
        return productRepo.findCategoryByStoreId(user.getStoreId());
    }
    @Override
    public Integer deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).get();
        product.setStatus(0);
        productRepo.save(product);
        return 1;
    }



}
