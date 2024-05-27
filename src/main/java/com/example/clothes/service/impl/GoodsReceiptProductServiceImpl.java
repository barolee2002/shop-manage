package com.example.clothes.service.impl;

import com.example.clothes.dto.GoodsReiceptProductDTO;
import com.example.clothes.entity.GoodsReceiptProduct;
import com.example.clothes.exception.AppException;
import com.example.clothes.exception.Errors;
import com.example.clothes.repository.GoodsReceiptProductRepository;
import com.example.clothes.repository.ProductAttributeRepository;
import com.example.clothes.service.GoodsReceiptProductService;
import com.example.clothes.service.ProductAttributeService;
import com.example.clothes.service.ProductInventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodsReceiptProductServiceImpl  implements GoodsReceiptProductService {
    private final GoodsReceiptProductRepository goodsReceiptProductRepository;
    private final ModelMapper mapper = new ModelMapper();
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeService productAttributeService;
    private final ProductInventoryService productInventoryService;
    private Date current = new Date();
    @Override
    public GoodsReiceptProductDTO create(GoodsReiceptProductDTO dto, Long receiptId) {
        GoodsReceiptProduct product;

        Optional<GoodsReceiptProduct> existingProductOpt = goodsReceiptProductRepository.findById(dto.getId());
        if(existingProductOpt.isPresent()) {
            product = existingProductOpt.get();
            mapper.map(dto, product);
        } else {
            product = mapper.map(dto, GoodsReceiptProduct.class);
        }

        if(productAttributeRepository.findById(dto.getProductAttribute().getId()).isEmpty()) {
            throw new AppException(Errors.NOT_FOUND);
        }

        product.setProductAttributeId(dto.getProductAttribute().getId());
        product.setReceiptId(receiptId);

        product = goodsReceiptProductRepository.save(product);
        return mapper.map(product, GoodsReiceptProductDTO.class);
    }
    @Override
    public List<GoodsReiceptProductDTO> getByGoodsReceipt(Long receiptId) {
        List<GoodsReceiptProduct> products = goodsReceiptProductRepository.findByReceiptId(receiptId);
        List<GoodsReiceptProductDTO> response = products.stream().map(product -> {
            GoodsReiceptProductDTO dto = mapper.map(product,GoodsReiceptProductDTO.class);
            dto.setProductAttribute(productAttributeService.getDetail(product.getProductAttributeId()));
            return dto;
        }).collect(Collectors.toList());
        return response;
    }
    @Override
    public Long delete(Long receiptProductId) {
        Optional<GoodsReceiptProduct> product = goodsReceiptProductRepository.findById(receiptProductId);
        if(product.isPresent()) {
            goodsReceiptProductRepository.delete(product.get());
            return receiptProductId;
        }
        return 0L;
    }
    @Override
    public String InventoryIn(Long orderId, Long inventoryId) {
        List<GoodsReceiptProduct> products = goodsReceiptProductRepository.findByReceiptId(orderId);
        products.stream().forEach(product -> {
            productInventoryService.updateQuantity(inventoryId,product.getProductAttributeId(), product.getQuantity());
        });
        return "success";
    }

}
