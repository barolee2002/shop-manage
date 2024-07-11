package com.example.shopmanament.controller;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.Response;
import com.example.shopmanament.dto.SupplierDTO;
import com.example.shopmanament.service.SupplierService;
import com.example.shopmanament.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;
    @PostMapping("/creating")
    public Response<SupplierDTO> create (@RequestBody SupplierDTO supplierDTO) {
        return new Response<>(HttpStatus.OK.value(), supplierService.create(supplierDTO));
    }
    @GetMapping("/get-all/{storeId}")
    public Response<List<SupplierDTO>> getAll(@PathVariable Long storeId) {
        return new Response<>(HttpStatus.OK.value(), supplierService.getALl(storeId));
    }
    @GetMapping("/get-detail/{supplierId}")
    public Response<SupplierDTO> getDetail(@PathVariable Long supplierId) {
        return new Response<>(HttpStatus.OK.value(), supplierService.getDetail(supplierId));
    }
    @PutMapping("/update")
    public Response<SupplierDTO> update (@RequestBody SupplierDTO supplierDTO) {
        return new Response<>(HttpStatus.OK.value(), supplierService.update(supplierDTO));

    }
    @PutMapping("/delete/{supplierId}")
    public Response<Long> delete(@PathVariable Long supplierId) {
        return new Response<>(HttpStatus.OK.value(),supplierService.delete(supplierId));
    }

    @GetMapping("/list-all/{storeId}")
    public Response<BasePage<SupplierDTO>> getAll (
            @PathVariable Long storeId,
            @RequestParam(required = false) Long staffId,
            @RequestParam(required = false)  String searchString,
            @RequestParam( required = false, defaultValue = Constant.SMALL_NUMBER) BigDecimal fromTotal,
            @RequestParam( required = false, defaultValue = Constant.BIG_NUMBER) BigDecimal toTotal,
            @RequestParam( required = false, defaultValue = "1") Integer page,
            @RequestParam( required = false, defaultValue = "10") Integer pageSize
    ) {
        return new Response<>(HttpStatus.OK.value(), supplierService.getAllByFilter(storeId, searchString, fromTotal,toTotal,page,pageSize));
    }
}
