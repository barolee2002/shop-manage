package com.example.clothes.controller;

import com.example.clothes.dto.BasePage;
import com.example.clothes.dto.ProductAttributeDTO;
import com.example.clothes.dto.Response;
import com.example.clothes.service.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-attributes")
@RequiredArgsConstructor
public class ProductAttributeController {
    private final ProductAttributeService productAttributeService;
    @PostMapping("/creating/{productId}")
    public Response<ProductAttributeDTO> create(@RequestBody ProductAttributeDTO attributeDTO, @PathVariable Long productId) {
        return new Response<>(HttpStatus.OK.value(), productAttributeService.create(attributeDTO,productId));
    }
    @PutMapping("updating/{attibuteId}")
    public Response<ProductAttributeDTO> update(
            @PathVariable("attibuteId") Long attributeId,
            @RequestBody ProductAttributeDTO attributeDTO
            ) {
        return new Response<>(HttpStatus.OK.value(), productAttributeService.update(attributeId,attributeDTO));
    }
    @PutMapping("/delete/{attibuteId}")
    public Response<Long> delete(@PathVariable("attibuteId") Long attributeId) {
        return new Response<>(HttpStatus.OK.value(), productAttributeService.delete(attributeId));
    }
    @GetMapping("/{productId}/query")
    public Response<List<ProductAttributeDTO>> getAllAttributeByProduct(
            @PathVariable("productId") Long productId
    ) {
        return new Response<>(HttpStatus.OK.value(), productAttributeService.getAllAttributeByProduct(productId));
    }

    @GetMapping("/detail/{productId}")
    public Response<ProductAttributeDTO> getDetail(@PathVariable("productId") Long productId) {
        return new Response<>(HttpStatus.OK.value(), productAttributeService.getDetail(productId));
    }
}
