package com.example.clothes.service.impl;

import com.example.clothes.entity.GoodsReceiptProduct;
import com.example.clothes.repository.GoodsReceiptRepository;
import com.example.clothes.service.GoodsReceiptService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsReceiptServiceImpl implements GoodsReceiptService {
    private final GoodsReceiptRepository goodsReceiptRepo;
    private final ModelMapper mapper = new ModelMapper();
}
