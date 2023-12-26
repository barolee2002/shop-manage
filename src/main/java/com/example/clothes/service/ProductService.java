package com.example.clothes.service;

import com.example.clothes.dto.ProductDTO;

public interface ProductService {
    ProductDTO create(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO, Long productId);

    ProductDTO getDetail(Long productId);
}
