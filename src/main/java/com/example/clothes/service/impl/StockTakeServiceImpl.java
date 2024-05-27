package com.example.clothes.service.impl;

import com.example.clothes.dto.*;
import com.example.clothes.entity.StockTake;
import com.example.clothes.exception.AppException;
import com.example.clothes.exception.Errors;
import com.example.clothes.repository.StockTakeRepository;
import com.example.clothes.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockTakeServiceImpl implements StockTakeService {
    private final StockTakeRepository stockTakeRepository;
    private final StockTakeDetailService stockTakeDetailService;
    private final ActionHistoryService actionHistoryService;
    private final UserService userService;
    private final InventoryService inventoryService;
    private final ProductInventoryService productInventoryService;
    private final ModelMapper mapper = new ModelMapper();
    private Date current = new Date();
    private String getCode(Long initialValue) {
        String response = "KH" + String.format("%06d", initialValue);
        Optional<StockTake> entity = stockTakeRepository.findByCode(response);
        if(entity.isEmpty()) {
            return response;
        }
        return getCode(initialValue+1);
    }
    @Override
    @Transactional
    public StockTakeDto create(CookieDto cookieDto, StockTakeDto request) {
        StockTake entity;
        Boolean check = true;
        Optional<StockTake> existingEntityOpt = stockTakeRepository.findById(request.getId());
        if(existingEntityOpt.isPresent()) {
            entity = existingEntityOpt.get();
            mapper.map(request, entity);
            List<StockTakeDetailDto> existingStokeTakeDetal = stockTakeDetailService.getByStockTake(entity.getId());
            List<Long> oldStokeTakeDetailId = existingStokeTakeDetal.stream().map(stockTakeDetailDto -> stockTakeDetailDto.getId()).collect(Collectors.toList());
            oldStokeTakeDetailId.stream().forEach(oldStokeTake -> {
                stockTakeDetailService.delete(oldStokeTake);
            });
            actionHistoryService.create(cookieDto, "Cập nhập phiếu nhập kho: " + entity.getCode());
            check = false;
        }
        else {
            entity = mapper.map(request, StockTake.class);

        }

        Long count = stockTakeRepository.countAllByStoreId(request.getStoreId());
        if(request.getCode() == null || request.getCode() == "") {
            entity.setCode(getCode(count));
        }
        entity.setCreateUser(request.getCreateUser().getId());
        entity.setConfirmUser(request.getConfirmUser().getId());
        entity.setInventoryId(request.getInventory().getId());
        entity.setCreateAt(current);
        entity.setStatus(0);
        entity = stockTakeRepository.save(entity);
        if(check) {
            actionHistoryService.create(cookieDto, "Tạo phiếu nhập kho: " + entity.getCode());
        }

        StockTake finalEntity = entity;
        request.getDetails().stream().forEach(detail -> {
            stockTakeDetailService.createOrUpdate(detail, finalEntity.getId());
        });

        return mapper.map(entity, StockTakeDto.class);

    }
    @Override
    public BasePage<StockTakeDto> getAll(Long storeId,String srearchString, Long confirmUser, Long createUser, Long inventoryId, Integer status, String createFromTime, String createToTime, String updateFromTime, String updateToTime, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Integer statusFilter = null;
        if(status == 0 || status == 1) {
            statusFilter = status;
        }
        Page<StockTake> stockTakePage = stockTakeRepository.getAll(storeId,srearchString,createUser,confirmUser,inventoryId,statusFilter,createFromTime,createToTime,updateFromTime,updateToTime, pageable);
        List<StockTake> entities = stockTakePage.get().toList();
        List<StockTakeDto> response = entities.stream().map(entity -> {
            StockTakeDto dto = mapper.map(entity, StockTakeDto.class);
            if(entity.getInventoryId() != null && entity.getInventoryId() != 0) {
                dto.setInventory(inventoryService.getDetail(entity.getInventoryId()));
            }
            if(entity.getCreateUser() != null && entity.getCreateUser() != 0) {
                dto.setCreateUser(userService.getInfo(entity.getCreateUser()));
            }
            if(entity.getConfirmUser() != null && entity.getConfirmUser() != 0) {
                dto.setConfirmUser(userService.getInfo(entity.getConfirmUser()));
            }
            return dto;
        }).collect(Collectors.toList());
        BasePage<StockTakeDto> dataPage = new BasePage<>();
        MetaData metaData =  new MetaData();
        metaData.setElements(stockTakePage.getNumberOfElements());
        metaData.setTotalElements(stockTakePage.getTotalElements());
        metaData.setTotalPages(stockTakePage.getTotalPages());
        dataPage.setMetaData(metaData);
        dataPage.setData(response);
        return dataPage;
    }
    @Override
    public StockTakeDto getDetail(Long stockTakeId) {
        Optional<StockTake> stockTake = stockTakeRepository.findById(stockTakeId);
        if(stockTake.isEmpty()) {
            throw new AppException(Errors.NOT_FOUND);
        }
        StockTakeDto response = mapper.map(stockTake, StockTakeDto.class);
        if(stockTake.get().getInventoryId() != null && stockTake.get().getInventoryId() != 0) {
            response.setInventory(inventoryService.getDetail(stockTake.get().getInventoryId()));
        }
        if(stockTake.get().getCreateUser() != null && stockTake.get().getCreateUser() != 0) {
            response.setCreateUser(userService.getInfo(stockTake.get().getCreateUser()));
        }
        if(stockTake.get().getConfirmUser() != null && stockTake.get().getConfirmUser() != 0) {
            response.setConfirmUser(userService.getInfo(stockTake.get().getConfirmUser()));
        }
        response.setDetails(stockTakeDetailService.getByStockTake(stockTakeId));
        return response;
    }
    @Override
    public Long delete(CookieDto cookieDto, Long stockTakeId) {
        Optional<StockTake> stockTake = stockTakeRepository.findById(stockTakeId);
        if(stockTake.isEmpty()) {
            throw new AppException(Errors.NOT_FOUND);
        }
        if(stockTake.get().getStatus() == 1) {
            throw new AppException(Errors.NOT_PERMITTED);
        }
        stockTakeRepository.delete(stockTake.get());
        actionHistoryService.create(cookieDto, "Xóa phiếu điều chỉnh: " + stockTake.get().getCode());
        return stockTake.get().getId();
    }
    @Override
    @Transactional
    public Long updateInventory(CookieDto cookieDto, Long stockTakeId) {
        Optional<StockTake> stockTake = stockTakeRepository.findById(stockTakeId);
        if(stockTake.isEmpty()) {
            throw new AppException(Errors.NOT_FOUND);
        }
        stockTake.get().setStatus(1);
        List<StockTakeDetailDto> dtos = stockTakeDetailService.getByStockTake(stockTake.get().getId());
        dtos.stream().forEach(dto -> {
            productInventoryService.updateQuantity(dto.getProductAttribute().getId(),stockTake.get().getInventoryId(), dto.getActualQuantity());
        });
        stockTakeRepository.save(stockTake.get());
        actionHistoryService.create(cookieDto, "Cập nhập kho bằng phiếu điều chỉnh: " + stockTake.get().getCode());

        return stockTake.get().getId();
    }
}
