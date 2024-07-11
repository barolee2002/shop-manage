package com.example.shopmanament.service.impl;

import com.example.shopmanament.dto.*;
import com.example.shopmanament.dto.response.ProductAttributeResponseDto;
import com.example.shopmanament.entity.Product;
import com.example.shopmanament.entity.ProductAttribute;
import com.example.shopmanament.entity.ProductInventory;
import com.example.shopmanament.entity.StockTake;
import com.example.shopmanament.repository.ProductAttributeRepository;
import com.example.shopmanament.repository.ProductRepository;
import com.example.shopmanament.service.ProductAttributeService;
import com.example.shopmanament.service.ProductInventoryService;
import com.example.shopmanament.utils.Transform;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private String getCode(Long initialValue) {
        String response = "SP" + String.format("%06d", initialValue);
        Optional<StockTake> entity = productAttributeRepo.findByCode(response);
        if(entity.isEmpty()) {
            return response;
        }
        return getCode(initialValue+1);
    }
    public Long getCountProduct() {
        List<Product> products = productRepo.findAll();
        return products.stream().reduce(0L,(total, product) -> sum(total,productAttributeRepo.countByProductId(product.getId())), Long::sum);
    }
    @Override
    @Transactional
    public ProductAttributeDTO create(ProductAttributeDTO attributeDTO,Long storeId, Long productId) {
        ProductAttribute attribute = new ProductAttribute();
        attribute = mapper.map(attributeDTO, ProductAttribute.class);
        attribute.setProductId(productId);
        if(attributeDTO.getCode() == null) {
            attribute.setCode(getCode(getCountProduct()));
        }
        attribute.setStatus(1);
        attribute.setOtherAttribute(transformData.arrayToString(attributeDTO.getOtherAttribute()));
        attribute = productAttributeRepo.save(attribute);
        ProductAttributeDTO response = mapper.map(attribute, ProductAttributeDTO.class);
        productInventoryService.createNewProduct(attribute.getId(), storeId);

        return response;

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
        attribute.setOtherAttribute(transformData.arrayToString(attributeDTO.getOtherAttribute()));
        return mapper.map(productAttributeRepo.save(attribute), ProductAttributeDTO.class);

    }
    @Override
    public ProductAttributeDTO getDetail( Long attributeId) {
        ProductAttribute attribute = productAttributeRepo.findById((attributeId)).get();
        ProductAttributeDTO dto = mapper.map(attribute, ProductAttributeDTO.class);
        dto.setInventoryList(productInventoryService.getAllByProductId(attributeId));
        dto.setOtherAttribute(transformData.stringToArray(attribute.getOtherAttribute()));
        Product product = productRepo.findById(attribute.getProductId()).get();
        dto.setProductName(product.getName());

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
    public List<ProductAttributeResponseDto> getByProductAndInventory(Long productId, Long inventoryId, String searchString) {
        List<ProductAttribute> productAttributes = productAttributeRepo.getByProductIdAndCode(searchString,productId);
        List<ProductAttributeResponseDto> response = new ArrayList<ProductAttributeResponseDto>();
        response = productAttributes.stream().map(attribute -> {
            ProductAttributeResponseDto attributeResponseDto = mapper.map(attribute, ProductAttributeResponseDto.class);
            attributeResponseDto.setOtherAttribute(transformData.stringToArray(attribute.getOtherAttribute()));

            ProductAttributeInventoryDTO productInventory = productInventoryService.getByProductAndInventory(attribute.getId(), inventoryId);
            if(productInventory != null) {
                attributeResponseDto.setQuantity(productInventory.getQuantity());

            }
            return attributeResponseDto;

        }).collect(Collectors.toList());
        return response;
    }
    @Override
    public BigDecimal minPriceByProduct(Long productId){
        BigDecimal response = productAttributeRepo.maxCostPrice(productId);
        if(response == null) {
            return BigDecimal.valueOf(0);
        }
        return response;
    }
    @Override
    public BigDecimal maxPriceByProduct(Long productId){
        BigDecimal response = productAttributeRepo.minCostPrice(productId);
        if(response == null) {
            return BigDecimal.valueOf(0);
        }
        return response;
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
    @Override
    public BasePage<ProductInventoryManamentDto> getDetailManament(Long inventoryId, String searchString, String time,Integer minQuantity, Integer quantity, Integer page, Integer pageSize) {
        Date searchTime = new Date();
        if(time.equals("WEEK")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(searchTime);
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
            searchTime = calendar.getTime();
        }
        if(time.equals("MONTH")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(searchTime);
            calendar.add(Calendar.MONTH, -1);
            searchTime = calendar.getTime();
        }
        if(time.equals("THREE_MONTH")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(searchTime);
            calendar.add(Calendar.MONTH, -3);
            searchTime = calendar.getTime();
        }
        if(time.equals("ALL")) {
            searchTime = new Date();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(searchTime);
        Page<ProductInventory> entityPage = productInventoryService.inventoryProductManament(inventoryId,searchString,formattedDate,minQuantity,quantity,page,pageSize);
        List<ProductInventoryManamentDto> dtoResponse = entityPage.get().toList().stream().map(entity -> {
            ProductAttribute attribute = productAttributeRepo.findById(entity.getProductId()).get();
            ProductInventoryManamentDto dto = mapper.map(attribute, ProductInventoryManamentDto.class);
            dto.setOtherAttribute(transformData.stringToArray(attribute.getOtherAttribute()));
            Product product = productRepo.findById(attribute.getProductId()).get();
            dto.setProductName(product.getName());
            dto.setProductBrand(product.getBrand());
            dto.setProductCategory(product.getCategory());
            dto.setQuantity(entity.getQuantity());
            return dto;
        }).collect(Collectors.toList());
        BasePage<ProductInventoryManamentDto> response = new BasePage<ProductInventoryManamentDto>();
        MetaData metaData =  new MetaData();
        metaData.setTotalElements(entityPage.getTotalElements());
        metaData.setTotalPages(entityPage.getTotalPages());
        metaData.setElements(entityPage.getNumberOfElements());
        response.setMetaData(metaData);
        response.setData(dtoResponse);
        return response;
    }


}
