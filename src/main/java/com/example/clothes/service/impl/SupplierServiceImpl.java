package com.example.clothes.service.impl;

import com.example.clothes.dto.SupplierDTO;
import com.example.clothes.entity.Supplier;
import com.example.clothes.repository.SupplierRepository;
import com.example.clothes.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepo;
    private final ModelMapper mapper =  new ModelMapper();
    @Override
    public SupplierDTO create(SupplierDTO dto) {
        Supplier supplier = mapper.map(dto, Supplier.class);
        supplier.setStatus(1);
        return mapper.map(supplierRepo.save(supplier), SupplierDTO.class);
    }
    @Override
    public SupplierDTO update(SupplierDTO dto) {
        Supplier supplier = supplierRepo.findById(dto.getId()).get();
        supplier = mapper.map(dto, Supplier.class);
        return mapper.map(supplierRepo.save(supplier), SupplierDTO.class);
    }
    @Override
    public Long delete (Long supplierId) {
        Supplier supplier = supplierRepo.findById(supplierId).get();
        supplier.setStatus(0);
        supplierRepo.save(supplier);
        return supplierId;
    }
    @Override
    public SupplierDTO getDetail(Long supplierId) {
        Supplier supplier = supplierRepo.findById(supplierId).get();
        return mapper.map(supplier, SupplierDTO.class);
    }
}
