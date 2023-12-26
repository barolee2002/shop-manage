package com.example.clothes.controller;

import com.example.clothes.dto.ProductDTO;
import com.example.clothes.dto.Response;
import com.example.clothes.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("/creating")
    public Response<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return new Response<>(HttpStatus.OK.value(), productService.create(productDTO));
    }
    @PutMapping("/updating/{productId}")
    public Response<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        return new Response<>(HttpStatus.OK.value(), productService.update(productDTO, productId));
    }

}
