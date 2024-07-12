package com.example.shopmanament.service.impl;

import com.example.shopmanament.dto.*;
import com.example.shopmanament.dto.response.ProductResponseDto;
import com.example.shopmanament.entity.Product;
import com.example.shopmanament.exception.AppException;
import com.example.shopmanament.exception.Errors;
import com.example.shopmanament.repository.ProductRepository;
import com.example.shopmanament.repository.UserRepository;
import com.example.shopmanament.service.ActionHistoryService;
import com.example.shopmanament.service.ProductAttributeService;
import com.example.shopmanament.service.ProductService;
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
    private final ActionHistoryService actionHistoryService;
    private final UserRepository userRepo;
    @Override
    @Transactional
    public ProductDTO create(CookieDto cookieDto,ProductDTO productDTO) {
        Product product =  mapper.map(productDTO, Product.class);
        product.setStatus(1);
        product.setCreateAt(new Date());
        product = productRepo.save(product);
        Product finalProduct = product;

        List<ProductAttributeDTO> attributeDTOS =  productDTO.getAttributes().stream().map((attribute) ->  attributeService.create(attribute,productDTO.getStoreId(), finalProduct.getId())).collect(Collectors.toList());
        ProductDTO response = mapper.map(product, ProductDTO.class);
        response.setAttributes(attributeDTOS);
        actionHistoryService.create(cookieDto, "Tạo hàng hóa: " +product.getName());

        return response;
    }
    @Override
    @Transactional
    public ProductDTO update(CookieDto cookieDto,ProductDTO productDTO, Long productId) {
        Product product = productRepo.findById(productId).get();
        if(product.getStatus() != 1) {
            throw new AppException(Errors.NOT_FOUND);
        }
        product = mapper.map(productDTO, Product.class);
        productDTO.getAttributes().stream().filter(attribute -> attribute.getId() != 0).map(attribute -> {
            return attributeService.update(attribute.getId(), attribute);
        });
        product = productRepo.save(product);
        actionHistoryService.create(cookieDto, "Cập nhập hàng hóa: " +product.getName());

        return getDetail(product.getId());
    }
    @Override
    public ProductDTO getDetail(Long productId) {
        Product product = productRepo.findById((productId)).get();
        if(product.getStatus() != 1) {
            throw new AppException(Errors.NOT_FOUND);
        }
        ProductDTO productDTO = mapper.map(product,ProductDTO.class);
        
        productDTO.setAttributes(attributeService.getAllAttributeByProduct(productId));
        return productDTO;
    }
    @Override
    public BasePage<ProductResponseDto> get(String searchString, Long inventoryId, String category, Long storeId, String fromTime, String toTime, Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        if(inventoryId== null || inventoryId == 0 ) {
            throw  new AppException(Errors.NOT_PERMITTED);
        }
        Page<Product> productPage = productRepo.findAll( searchString, category,storeId,fromTime, toTime, pageable);
        List<ProductResponseDto> dtos = productPage.get().map(product -> mapper.map(product, ProductResponseDto.class)).toList();
        dtos = dtos.stream().map(dto -> {
            dto.setAttributes(attributeService.getByProductAndInventory(dto.getId(), inventoryId, searchString));
            dto.setMinPrice(attributeService.minPriceByProduct(dto.getId()));
            dto.setMaxPrice(attributeService.maxPriceByProduct(dto.getId()));
            dto.setTotalQuantity(attributeService.totalQuantityByInventory(dto.getId(),inventoryId));
            return dto;
        }).collect(Collectors.toList());
        BasePage<ProductResponseDto> dataPage = new BasePage<>();
        MetaData metaData =  new MetaData();
        metaData.setTotalElements(productPage.getTotalElements());
        metaData.setTotalPages(productPage.getTotalPages());
        metaData.setElements(productPage.getNumberOfElements());
        dataPage.setMetaData(metaData);
        dataPage.setData(dtos);
        return dataPage;

    }
    @Override
    public List<String> getAllCatgories(Long userId) {
        return productRepo.findCategoryByStoreId(userId);
    }
    @Override
    public List<String> getAllBrands(Long userId) {
        return productRepo.findBrandByStoreId(userId);
    }
    @Override
    public Integer deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).get();
        product.setStatus(0);
        List<ProductAttributeDTO> attributes = attributeService.getAllAttributeByProduct(productId);
        attributes.stream().forEach(attribute -> {
            attributeService.delete(attribute.getId());
        });
        productRepo.save(product);
        return 1;
    }
}
