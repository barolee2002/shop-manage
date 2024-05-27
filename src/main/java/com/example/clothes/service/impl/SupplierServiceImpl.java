package com.example.clothes.service.impl;

import com.example.clothes.dto.SupplierDTO;
import com.example.clothes.entity.Supplier;
import com.example.clothes.repository.SupplierRepository;
import com.example.clothes.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
        SupplierDTO response = mapper.map(supplier, SupplierDTO.class);
        return response;
    }

    @Override
    public List<SupplierDTO> getALl(Long storeId) {
        List<Supplier> entities = supplierRepo.findByStoreId((storeId));
        List<SupplierDTO> response = Arrays.asList(mapper.map(entities, SupplierDTO[].class));
        return response;
    }
    @Override
    public String debit(Long supplierId, BigDecimal deptMoney) {
        Supplier entity = supplierRepo.findById(supplierId).get();
        entity.setDeptMoney(entity.getDeptMoney().add(deptMoney));
        supplierRepo.save(entity);
        return "susscess";
    }
    @Override
    public String pay(Long supplierId, BigDecimal money) {
        Supplier entity = supplierRepo.findById(supplierId).get();
        entity.setDeptMoney(entity.getDeptMoney().subtract(money));
        supplierRepo.save(entity);
        return "susscess";
    }
}
