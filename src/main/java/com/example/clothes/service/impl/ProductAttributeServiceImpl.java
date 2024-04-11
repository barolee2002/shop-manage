package com.example.clothes.service.impl;

import com.example.clothes.dto.ProductAttributeDTO;
import com.example.clothes.dto.ProductAttributeInventoryDTO;
import com.example.clothes.dto.response.ProductAttributeResponseDto;
import com.example.clothes.entity.Product;
import com.example.clothes.entity.ProductAttribute;
import com.example.clothes.repository.ProductAttributeRepository;
import com.example.clothes.repository.ProductRepository;
import com.example.clothes.service.ProductAttributeService;
import com.example.clothes.service.ProductInventoryService;
import com.example.clothes.utils.Transform;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.lang.Long.sum;

@Service
@RequiredArgsConstructor
public class ProductAttributeServiceImpl implements ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepo;
    private final ProductInventoryService productInventoryService;
    private final ProductRepository productRepo;
    private final Transform transformData = new Transform();
    private final ModelMapper mapper = new ModelMapper();

    public Long getCountProduct() {
        List<Product> products = productRepo.findAll();
        return products.stream().reduce(0L,(total, product) -> sum(total,productAttributeRepo.countByProductId(product.getId())), Long::sum);
    }
    @Override
    @Transactional
    public ProductAttributeDTO create(ProductAttributeDTO attributeDTO, Long productId) {
//        if(productAttributeRepo.findDupplicateAttribute(attributeDTO.getProductId(), attributeDTO.getSize(), attributeDTO.getMaterial(), attributeDTO.getVariation(), attributeDTO.getOrigin()) != null) {
//            throw new AppException(Errors.EXIST_ATTRIBUTE);
//        }
        ProductAttribute attribute = new ProductAttribute();
        attribute = mapper.map(attributeDTO, ProductAttribute.class);
        attribute.setProductId(productId);
        if(attributeDTO.getCode() == null) {
            attribute.setCode("P" + String.format("%08d", getCountProduct() + 1));
        }
        attribute.setStatus(1);
        attribute.setOtherAttribute(transformData.arrayToString(attributeDTO.getOtherAttribute()));
        return mapper.map(productAttributeRepo.save(attribute), ProductAttributeDTO.class);

    }

    @Override
    public List<ProductAttributeDTO> getAllAttributeByProduct(Long productId) {
        List<ProductAttributeDTO> productAttributeDTOS = new ArrayList<ProductAttributeDTO>();
        List<ProductAttribute> attributes = productAttributeRepo.findByProductIdAndStatus(productId);
         productAttributeDTOS = attributes.stream().map(attribute -> {
             ProductAttributeDTO attributeDTO =  mapper.map(attribute, ProductAttributeDTO.class);
             attributeDTO.setInventoryList(productInventoryService.getAllByProductId(attribute.getId()));
             if(attribute.getOtherAttribute() != null) {
                 attributeDTO.setOtherAttribute(transformData.stringToArray(attribute.getOtherAttribute()));
             }

             return attributeDTO;
         }).collect(Collectors.toList());
        return productAttributeDTOS;
    }
    @Override
    public ProductAttributeDTO update (Long attributeId, ProductAttributeDTO attributeDTO) {
        ProductAttribute attribute = productAttributeRepo.findById((attributeId)).get();
        attribute = mapper.map(attributeDTO, ProductAttribute.class);
        return mapper.map(productAttributeRepo.save(attribute), ProductAttributeDTO.class);

    }
    @Override
    public ProductAttributeDTO getDetail( Long attributeId) {
        ProductAttribute attribute = productAttributeRepo.findById((attributeId)).get();
        ProductAttributeDTO dto = mapper.map(attribute, ProductAttributeDTO.class);
        dto.setInventoryList(productInventoryService.getAllByProductId(attributeId));
        dto.setOtherAttribute(transformData.stringToArray(attribute.getOtherAttribute()));
        return dto;
    }
    @Override
    public Long delete(Long attributeId) {
        ProductAttribute attribute = productAttributeRepo.findById((attributeId)).get();
        attribute.setStatus(0);
        productAttributeRepo.save(attribute);
        return attribute.getId();
    }
    @Override
    public List<ProductAttributeResponseDto> getByProductAndInventory(Long productId, Long inventoryId) {
        List<ProductAttribute> productAttributes = productAttributeRepo.findByProductId(productId);
        List<ProductAttributeResponseDto> response = new ArrayList<ProductAttributeResponseDto>();
        response = productAttributes.stream().map(attribute -> {
            ProductAttributeResponseDto attributeResponseDto = mapper.map(attribute, ProductAttributeResponseDto.class);
            attributeResponseDto.setOtherAttribute(transformData.stringToArray(attribute.getOtherAttribute()));

            ProductAttributeInventoryDTO productInventory = productInventoryService.getByProductAndInventory(attribute.getId(), inventoryId);
            if(productInventory != null) {
                attributeResponseDto.setCostPrice(productInventory.getCostPrice());
                attributeResponseDto.setSellPrice(productInventory.getSellPrice());
                attributeResponseDto.setQuantity(productInventory.getQuantity());

            }
            return attributeResponseDto;

        }).collect(Collectors.toList());
        return response;
    }
    @Override
    public BigDecimal minPriceByProductAndInventory(Long productId, Long inventoryId){
        List<ProductAttribute> productAttributes = productAttributeRepo.findByProductId(productId);
        Optional<BigDecimal> response = productAttributes.stream().map(attribute -> {
            ProductAttributeInventoryDTO productInventory = productInventoryService.getByProductAndInventory(attribute.getId(), inventoryId);
            if(productInventory == null) {return BigDecimal.valueOf(0);}
            return productInventory.getSellPrice();
        }).min(Comparator.naturalOrder());
        return response.get();
    }
    @Override
    public BigDecimal maxPriceByProductAndInventory(Long productId, Long inventoryId){
        List<ProductAttribute> productAttributes = productAttributeRepo.findByProductId(productId);
        Optional<BigDecimal> response = productAttributes.stream().map(attribute -> {
            ProductAttributeInventoryDTO productInventory = productInventoryService.getByProductAndInventory(attribute.getId(), inventoryId);
            if(productInventory == null) {return BigDecimal.valueOf(0);}
            return productInventory.getSellPrice();
        }).max(Comparator.naturalOrder());
        return response.get();
    }
    @Override
    public Integer totalQuantityByInventory (Long inventoryId, Long productId){
        List<ProductAttribute> productAttributes = productAttributeRepo.findByProductId(productId);
        Integer response = productAttributes.stream().map(attribute -> {
            ProductAttributeInventoryDTO productInventory = productInventoryService.getByProductAndInventory(attribute.getId(), inventoryId);
            if(productInventory == null) {return 0;}
            return productInventory.getQuantity();
        }).mapToInt(Integer::intValue).sum();
        return response;
    }

}
