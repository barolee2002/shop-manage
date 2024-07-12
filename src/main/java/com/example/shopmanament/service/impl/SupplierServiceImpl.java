package com.example.shopmanament.service.impl;

import com.example.shopmanament.dto.BasePage;
import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.MetaData;
import com.example.shopmanament.dto.SupplierDTO;
import com.example.shopmanament.entity.GoodsReceipt;
import com.example.shopmanament.entity.Supplier;
import com.example.shopmanament.repository.GoodsReceiptRepository;
import com.example.shopmanament.repository.SupplierRepository;
import com.example.shopmanament.service.ActionHistoryService;
import com.example.shopmanament.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepo;
    private final ModelMapper mapper =  new ModelMapper();
    private final ActionHistoryService actionHistoryService;
    private final GoodsReceiptRepository goodsReceiptRepository;
    public Integer totalOrderBySupplier( Long supplierId) {
        List<GoodsReceipt> entities = goodsReceiptRepository.findBySupplierId(supplierId);
        return entities.size();
    }
    public BigDecimal totalOrderValueBySupplier(Long supplierId) {
        List<GoodsReceipt> entities = goodsReceiptRepository.findBySupplierId(supplierId);
        return entities.stream()
                .map(GoodsReceipt::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Override
    public SupplierDTO create(CookieDto cookieDto,SupplierDTO dto) {
        Supplier supplier = mapper.map(dto, Supplier.class);
        supplier.setStatus(1);
        supplier = supplierRepo.save(supplier);
        actionHistoryService.create(cookieDto, "Tạo nhà cung cấp: " +supplier.getName());

        return mapper.map(supplier, SupplierDTO.class);
    }
    @Override
    public SupplierDTO update(CookieDto cookieDto,SupplierDTO dto) {
        Supplier supplier = supplierRepo.findById(dto.getId()).get();
        supplier = mapper.map(dto, Supplier.class);
        supplier = supplierRepo.save(supplier);
        actionHistoryService.create(cookieDto, "Cập nhập nhà cung cấp: " +supplier.getName());

        return mapper.map(supplier, SupplierDTO.class);
    }
    @Override
    public Long delete (CookieDto cookieDto,Long supplierId) {
        Supplier supplier = supplierRepo.findById(supplierId).get();
        supplier.setStatus(0);
        actionHistoryService.create(cookieDto, "Xóa nhà cung cấp: " +supplier.getName());
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
    @Override
    public BasePage<SupplierDTO> getAllByFilter(Long storeId, String searchString, BigDecimal fromTotal, BigDecimal toTotal, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Supplier> supplierPage = supplierRepo.getAll(storeId,searchString, fromTotal,toTotal, pageable);
        List<Supplier> entities = supplierPage.get().collect(Collectors.toList());
        List<SupplierDTO> dtos= entities.stream().map(entity -> {
            SupplierDTO dto = mapper.map(entity, SupplierDTO.class);
            dto.setOrderQuantity(totalOrderBySupplier(entity.getId()));
            dto.setTotalOrderMoney(totalOrderValueBySupplier(entity.getId()));
            return dto;
        }).collect(Collectors.toList());
        BasePage<SupplierDTO> dataPage = new BasePage<>();
        MetaData metaData =  new MetaData();
        metaData.setTotalElements(supplierPage.getTotalElements());
        metaData.setTotalPages(supplierPage.getTotalPages());
        metaData.setElements(supplierPage.getNumberOfElements());
        dataPage.setMetaData(metaData);
        dataPage.setData(dtos);
        return dataPage;
    }
}
