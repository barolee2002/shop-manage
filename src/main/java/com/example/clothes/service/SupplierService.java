package com.example.clothes.service;

import com.example.clothes.dto.SupplierDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SupplierService {
    SupplierDTO create(SupplierDTO dto);

    SupplierDTO update(SupplierDTO dto);

    Long delete(Long supplierId);

    SupplierDTO getDetail(Long supplierId);

    List<SupplierDTO> getALl(Long storeId);

    String debit(Long supplierId, BigDecimal deptMoney);

    String pay(Long supplierId, BigDecimal money);
}
