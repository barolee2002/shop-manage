package com.example.shopmanament.service.impl;

import com.example.shopmanament.dto.*;
import com.example.shopmanament.entity.GoodsReceipt;
import com.example.shopmanament.exception.AppException;
import com.example.shopmanament.exception.Errors;
import com.example.shopmanament.repository.GoodsReceiptRepository;
import com.example.shopmanament.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodsReceiptServiceImpl implements GoodsReceiptService {
    private final GoodsReceiptRepository goodsReceiptRepo;
    private final ModelMapper mapper = new ModelMapper();
    private final GoodsReceiptProductService goodsReceiptProductService;
    private final InventoryService inventoryService;
    private final UserService userService;
    private final ActionHistoryService actionHistoryService;
    private final SupplierService supplierService;
    private Date current = new Date();
    @Override
    public BasePage<GoodsReceiptDTO> getAll(Long storeId, Long supplierId, Long bookingUserId, Long confirmUserId, Long payStatus, Long receiptStatus, Long inventoryId, String bookingFromTime, String bookngToTime, String receiptFromTime, String receiptToTime, BigDecimal fromTotal, BigDecimal toTotal, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<GoodsReceipt> goodsReceiptPage = goodsReceiptRepo.getAll(storeId,supplierId, bookingUserId, confirmUserId, inventoryId, payStatus, receiptStatus,bookingFromTime, bookngToTime, receiptFromTime, receiptToTime, fromTotal, toTotal, pageable);
        List<GoodsReceipt> entities = goodsReceiptPage.get().collect(Collectors.toList());
        List<GoodsReceiptDTO> dtos= entities.stream().map(entity -> {
            GoodsReceiptDTO dto = mapper.map(entity, GoodsReceiptDTO.class);
            if(entity.getBookingUserId() != null && entity.getBookingUserId() != 0) {
                dto.setBookingUser(userService.getInfo(entity.getBookingUserId()));
            }
            if(entity.getConfirmUserId() != null && entity.getConfirmUserId() != 0) {
                dto.setConfirmUser(userService.getInfo(entity.getConfirmUserId()));
            }
            dto.setInventory(inventoryService.getDetail(entity.getInventoryId()));
            dto.setSupplier(supplierService.getDetail(entity.getSupplierId()));
            return dto;
        }).collect(Collectors.toList());
        BasePage<GoodsReceiptDTO> dataPage = new BasePage<>();
        MetaData metaData =  new MetaData();
        metaData.setTotalElements(goodsReceiptPage.getTotalElements());
        metaData.setTotalPages(goodsReceiptPage.getTotalPages());
        metaData.setElements(goodsReceiptPage.getNumberOfElements());
        dataPage.setMetaData(metaData);
        dataPage.setData(dtos);
        return dataPage;
    }

    @Override
    @Transactional
    public GoodsReceiptDTO create(CookieDto cookieDto,GoodsReceiptDTO request) {
        GoodsReceipt entity;
        Optional<GoodsReceipt> existingEntityOpt = goodsReceiptRepo.findById(request.getId());
        if(existingEntityOpt.isPresent()) {
            entity = existingEntityOpt.get();
            mapper.map(request, entity);

            List<GoodsReiceptProductDTO> existingProducts = goodsReceiptProductService.getByGoodsReceipt(request.getId());
            List<Long> incomingProductIds = request.getProducts().stream()
                    .map(GoodsReiceptProductDTO::getId)
                    .collect(Collectors.toList());
            List<Long> productsToDelete = existingProducts.stream()
                    .map(GoodsReiceptProductDTO::getId)
                    .filter(id -> !incomingProductIds.contains(id))
                    .collect(Collectors.toList());
            productsToDelete.forEach(goodsReceiptProductService::delete);
        } else {
            entity = mapper.map(request, GoodsReceipt.class);
        }

        entity.setBookingUserId(request.getBookingUser().getId());
        if(request.getConfirmUser() != null) {
            entity.setConfirmUserId(request.getConfirmUser().getId());
        }
        entity.setInventoryId(request.getInventory().getId());
        entity.setSupplierId(request.getSupplier().getId());
        entity.setBookingDate(current);

        entity = goodsReceiptRepo.save(entity);

        if(!request.getProducts().isEmpty()){
            GoodsReceipt finalEntity = entity;
            request.getProducts().forEach(product -> goodsReceiptProductService.create(product, finalEntity.getId()));
        }
        supplierService.debit(entity.getSupplierId(), entity.getTotal());
        actionHistoryService.create(cookieDto, "Tạo đơn đặt hàng: " + entity.getCode());
        return mapper.map(entity, GoodsReceiptDTO.class);
    }
    @Override
    public GoodsReceiptDTO getDetail(Long goodsReceiptId) {
        Optional<GoodsReceipt> entity = goodsReceiptRepo.findById(goodsReceiptId);
        if(entity.isEmpty()) {
            throw new AppException((Errors.NOT_FOUND));
        }
        GoodsReceiptDTO response = mapper.map(entity, GoodsReceiptDTO.class);
        response.setInventory(inventoryService.getDetail(entity.get().getInventoryId()));
        if(entity.get().getConfirmUserId() != null && entity.get().getConfirmUserId() != 0) {
            response.setConfirmUser(userService.getInfo(entity.get().getConfirmUserId()));
        }
        if(entity.get().getBookingUserId() != null && entity.get().getBookingUserId() != 0) {
            response.setBookingUser(userService.getInfo(entity.get().getBookingUserId()));
        }
        response.setSupplier(supplierService.getDetail(entity.get().getSupplierId()));
        response.setProducts(goodsReceiptProductService.getByGoodsReceipt(entity.get().getId()));
        return response;

    }
    @Override
    public Long delete(CookieDto cookieDto,Long receiptId) {
        Optional<GoodsReceipt> product = goodsReceiptRepo.findById(receiptId);
        if(product.isPresent()) {
            goodsReceiptRepo.delete(product.get());
            return receiptId;
        }
        if(product.get().getPayStatus() == 1 || product.get().getReceiptStatus() == 1) {
            throw new AppException(Errors.INVALID_DATA);
        }
        actionHistoryService.create(cookieDto, "Xóa đơn đặt hàng: " + product.get().getCode());

        return 0L;
    }
    @Override
    public String payOrder(CookieDto cookieDto,Long orderId) {
        GoodsReceipt receipt = goodsReceiptRepo.findById(orderId).get();
        receipt.setPayStatus(1);
        supplierService.pay(receipt.getSupplierId(), receipt.getTotal());
        actionHistoryService.create(cookieDto, "Thanh toán đơn đặt hàng: " + receipt.getCode());
        goodsReceiptRepo.save(receipt);
        return "success";
    }

    @Override
    public String InventoryIn(CookieDto cookieDto,Long orderId) {
        GoodsReceipt receipt = goodsReceiptRepo.findById(orderId).get();
        if(receipt == null) {
            throw new AppException(Errors.NOT_FOUND);
        }
        goodsReceiptProductService.InventoryIn(receipt.getId(), receipt.getInventoryId());
        receipt.setReceiptStatus(1);
        if(cookieDto != null) {
            receipt.setConfirmUserId(cookieDto.getUserId());
        }
        actionHistoryService.create(cookieDto, "Nhập kho đơn đặt hàng: " + receipt.getCode());
        goodsReceiptRepo.save(receipt);
        return "success";
    }

}
