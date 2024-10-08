package com.example.shopmanament.service;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.SupplierDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SupplierService {
    SupplierDTO create(CookieDto cookieDto, SupplierDTO dto);

    SupplierDTO update(CookieDto cookieDto,SupplierDTO dto);

    Long delete(CookieDto cookieDto,Long supplierId);

    SupplierDTO getDetail(Long supplierId);

    List<SupplierDTO> getALl(Long storeId);

    String debit(Long supplierId, BigDecimal deptMoney);

    String pay(Long supplierId, BigDecimal money);


    BasePage<SupplierDTO> getAllByFilter(Long storeId, String searchString, BigDecimal fromTotal, BigDecimal toTotal, Integer page, Integer pageSize);
}
