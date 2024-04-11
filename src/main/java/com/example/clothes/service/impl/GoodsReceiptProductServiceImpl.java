package com.example.clothes.service.impl;

import com.example.clothes.dto.GoodsReiceptProductDTO;
import com.example.clothes.entity.GoodsReceiptProduct;
import com.example.clothes.repository.GoodsReceiptProductRepository;
import com.example.clothes.service.GoodsReceiptProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsReceiptProductServiceImpl  implements GoodsReceiptProductService {
    private final GoodsReceiptProductRepository goodsReceiptProductRepository;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public GoodsReiceptProductDTO create(GoodsReiceptProductDTO dto, Long receiptId) {
        GoodsReceiptProduct product = mapper.map(dto, GoodsReceiptProduct.class);
        product.setReceiptId(receiptId);
        return mapper.map(goodsReceiptProductRepository.save(product), GoodsReiceptProductDTO.class);
    }
    @Override
    public List<GoodsReiceptProductDTO> getByGoodsReceipt(Long receiptId) {
        List<GoodsReceiptProduct> products = goodsReceiptProductRepository.findByReceiptId(receiptId);
        return Arrays.asList(mapper.map(products, GoodsReiceptProductDTO[].class));
    }

}
