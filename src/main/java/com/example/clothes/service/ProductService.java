package com.example.clothes.service;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductDTO;

public interface ProductService {
    ProductDTO create(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO, Long productId);

    ProductDTO getDetail(Long productId);


    BasePage<ProductDTO> get(String name, String category, String code, Long userId, Integer page, Integer pageSize);
}
