package com.example.clothes.service;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductDTO;

import java.util.Date;
import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO, Long productId);

    ProductDTO getDetail(Long productId);

    BasePage<ProductDTO> get(String name, String category, String code, Long userId, String fromTime, String toTime, Integer page, Integer pageSize);

    List<String> getAllCatgories(Long userId);

    Integer deleteProduct(Long productId);
}
