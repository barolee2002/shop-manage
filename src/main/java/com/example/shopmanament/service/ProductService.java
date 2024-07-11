package com.example.shopmanament.service;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.ProductDTO;
import com.example.shopmanament.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductDTO create(CookieDto cookieDto,ProductDTO productDTO);

    ProductDTO update(CookieDto cookieDto,ProductDTO productDTO, Long productId);

    ProductDTO getDetail(Long productId);

    BasePage<ProductResponseDto> get(String name, Long inventoryId, String category, Long storeId, String fromTime, String toTime, Integer page, Integer pageSize);

    List<String> getAllCatgories(Long userId);

    List<String> getAllBrands(Long userId);

    Integer deleteProduct(Long productId);

}
