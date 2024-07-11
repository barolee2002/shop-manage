package com.example.shopmanament.controller;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.ProductDTO;
import com.example.shopmanament.dto.Response;
import com.example.shopmanament.dto.response.ProductResponseDto;
import com.example.shopmanament.service.ProductService;
import com.example.shopmanament.utils.Constant;
import com.example.shopmanament.utils.JsonParse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final JsonParse jsonParse = new JsonParse();
    @PostMapping("/creating")
    public Response<ProductDTO> createProduct(@CookieValue(value = "userInfo", required = false) String cookieDto,@RequestBody ProductDTO productDTO) {
        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);

        return new Response<>(HttpStatus.OK.value(), productService.create(dto,productDTO));
    }
    @PutMapping("/updating/{productId}")
    public Response<ProductDTO> updateProduct(@CookieValue(value = "userInfo", required = false) String cookieDto,@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        CookieDto dto = jsonParse.parseJsonToCookieDto(cookieDto);
        return new Response<>(HttpStatus.OK.value(), productService.update(dto,productDTO, productId));
    }
    @GetMapping("/all")
    public Response<BasePage<ProductResponseDto>> getAllProduct(
            @RequestParam( required = false, defaultValue = "") String searchString,
            @RequestParam(required = true, defaultValue = "0") Long inventoryId,
            @RequestParam( required = false, defaultValue = "") String category,
            @RequestParam( required = true) Long storeId,
            @RequestParam( required = false, defaultValue = "1") Integer page,
            @RequestParam( required = false, defaultValue = "10") Integer pageSize,
            @RequestParam( required = false, defaultValue = Constant.PAST_DATE) String fromTime,
            @RequestParam( required = false, defaultValue = Constant.FUTURE_DATE) String toTime
            ) {
        return new Response<>(HttpStatus.OK.value(), productService.get(searchString,inventoryId, category,storeId,fromTime,toTime,page,pageSize));
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
    @GetMapping("/brands/{userId}")
    public Response<List<String>> getAllBrands(@PathVariable("userId") Long userId) {
        return new Response<>(HttpStatus.OK.value(),productService.getAllBrands(userId));
    }
    @PutMapping("/delete/{id}")
    public Response<Integer> delete(@PathVariable("id") Long id) {
        return new Response<>(HttpStatus.OK.value(),productService.deleteProduct(id));
    }

}
