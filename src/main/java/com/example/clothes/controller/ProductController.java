package com.example.clothes.controller;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductDTO;
import com.example.clothes.dto.Response;
import com.example.clothes.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    @GetMapping("/all")
    public Response<BasePage<ProductDTO>> getAllProduct(
            @RequestParam( required = false, defaultValue = "") String name,
            @RequestParam( required = false, defaultValue = "") String category,
            @RequestParam( required = false, defaultValue = "") String code,
            @RequestParam( required = false, defaultValue = "") Long userId,
            @RequestParam( required = false, defaultValue = "") Integer page,
            @RequestParam( required = false, defaultValue = "") Integer pageSize
    ) {
        return new Response<>(HttpStatus.OK.value(), productService.get(name, category,code,userId,page,pageSize));
    }
    @GetMapping("/detail/{productId}")
    public Response<ProductDTO> getDetail(
            @PathVariable("productId") Long productId
    ) {
        return new Response<>(HttpStatus.OK.value(), productService.getDetail(productId));
    }

}
