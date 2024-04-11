package com.example.clothes.service;

import com.example.clothes.dto.SupplierDTO;

public interface SupplierService {
    SupplierDTO create(SupplierDTO dto);

    SupplierDTO update(SupplierDTO dto);

    Long delete(Long supplierId);

    SupplierDTO getDetail(Long supplierId);
}
