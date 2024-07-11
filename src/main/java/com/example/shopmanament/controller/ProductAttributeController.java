package com.example.shopmanament.controller;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.ProductAttributeDTO;
import com.example.shopmanament.dto.ProductInventoryManamentDto;
import com.example.shopmanament.dto.Response;
import com.example.shopmanament.service.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.shopmanament.utils.Constant.BIG_QUANTITY;

@RestController
@RequestMapping("/product-attributes")
@RequiredArgsConstructor
public class ProductAttributeController {
    private final ProductAttributeService productAttributeService;
    @PostMapping("/creating/{storeId}/product/{productId}")
    public Response<ProductAttributeDTO> create(@RequestBody ProductAttributeDTO attributeDTO, @PathVariable("storeId") Long storeId, @PathVariable("productId") Long productId) {
        return new Response<>(HttpStatus.OK.value(), productAttributeService.create(attributeDTO,storeId,productId));
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
    @GetMapping("/stock-management")
    public Response<BasePage<ProductInventoryManamentDto>> getStockManagement(
            @RequestParam(value = "inventoryId", required = true) Long inventoryId,
            @RequestParam(value = "searchString", required = false) String searchString,
            @RequestParam(value = "time", required = false, defaultValue = "ALL") String time,
            @RequestParam(value = "minQuantity", required = false, defaultValue = "0") Integer minQuantity,
            @RequestParam(value = "quantity", required = false, defaultValue = BIG_QUANTITY) Integer quantity,
            @RequestParam(value = "page", required = false,defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false,defaultValue = "10") Integer pageSize

    ) {
        return new Response<>(HttpStatus.OK.value(), productAttributeService.getDetailManament(inventoryId,searchString,time,minQuantity,quantity,page,pageSize));
    }
}
