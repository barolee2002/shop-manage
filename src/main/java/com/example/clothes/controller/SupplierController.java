package com.example.clothes.controller;

import com.example.clothes.dto.Response;
import com.example.clothes.dto.SupplierDTO;
import com.example.clothes.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;
    @PostMapping("/creating")
    public Response<SupplierDTO> create (@RequestBody SupplierDTO supplierDTO) {
        return new Response<>(HttpStatus.OK.value(), supplierService.create(supplierDTO));
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


}
