package com.example.clothes.controller;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductDTO;
import com.example.clothes.dto.Response;
import com.example.clothes.dto.response.ProductResponseDto;
import com.example.clothes.service.ProductService;
import com.example.clothes.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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
    public Response<BasePage<ProductResponseDto>> getAllProduct(
            @RequestParam( required = false, defaultValue = "") String searchString,
            @RequestParam(required = true, defaultValue = "1") Long inventoryId,
            @RequestParam( required = false, defaultValue = "") String category,
            @RequestParam( required = true) Long userId,
            @RequestParam( required = false, defaultValue = "1") Integer page,
            @RequestParam( required = false, defaultValue = "10") Integer pageSize,
            @RequestParam( required = false, defaultValue = Constant.PAST_DATE) String fromTime,
            @RequestParam( required = false, defaultValue = Constant.FUTURE_DATE) String toTime
            ) {
        return new Response<>(HttpStatus.OK.value(), productService.get(searchString,inventoryId, category,userId,fromTime,toTime,page,pageSize));
    }
    @GetMapping("/detail/{productId}")
    public Response<ProductDTO> getDetail(
            @PathVariable("productId") Long productId
    ) {
        return new Response<>(HttpStatus.OK.value(), productService.getDetail(productId));
    }
    @GetMapping("/categories/{userId}")
    public Response<List<String>> getAllCategories(@PathVariable("userId") Long userId) {
        return new Response<>(HttpStatus.OK.value(),productService.getAllCatgories(userId));
    }
    @PutMapping("/delete/{id}")
    public Response<Integer> delete(@PathVariable("id") Long id) {
        return new Response<>(HttpStatus.OK.value(),productService.deleteProduct(id));
    }

}
