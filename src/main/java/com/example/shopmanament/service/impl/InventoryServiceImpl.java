package com.example.shopmanament.service.impl;

import com.example.shopmanament.dto.CookieDto;
import com.example.shopmanament.dto.InventoryDto;
import com.example.shopmanament.dto.request.InventoryDTORequest;
import com.example.shopmanament.dto.response.UserDTOResponse;
import com.example.shopmanament.entity.*;
import com.example.shopmanament.repository.InventoryRepository;
import com.example.shopmanament.repository.InventoryUserRepository;
import com.example.shopmanament.repository.UserRepository;
import com.example.shopmanament.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepo;
    private final InventoryUserRepository inventoryUserRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper = new ModelMapper();
    private String getCode(Long initialValue) {
        String response = "I" + String.format("%06d", initialValue);
        Optional<Inventory> entity = inventoryRepo.findByCode(response);
        if(entity.isEmpty()) {
            return response;
        }
        return getCode(initialValue+1);
    }
    @Override
    public InventoryDto addInventory(CookieDto cookieDto, Long storeId, InventoryDTORequest inventoryDTORequest) {
        Inventory newInventory = mapper.map(inventoryDTORequest, Inventory.class);
        Long count = inventoryRepo.countByStoreId(storeId);
        newInventory.setCode(getCode(count));
        newInventory.setStatus(1);
        newInventory.setStoreId(storeId);
        newInventory = inventoryRepo.save(newInventory);
        return mapper.map(newInventory, InventoryDto.class);

    }
    @Override
    public InventoryDto update(CookieDto cookieDto,Long inventoryId, InventoryDTORequest inventoryDTORequest) {
        Inventory oldInventory = inventoryRepo.findById(inventoryId).get();
        oldInventory = mapper.map(inventoryDTORequest, Inventory.class);
        oldInventory = inventoryRepo.save(oldInventory);
        return mapper.map(oldInventory, InventoryDto.class);
    }
    @Override
    public Long delete(CookieDto cookieDto,Long inventoryId) {
        Inventory inventory = inventoryRepo.findById(inventoryId).get();
        inventory.setStatus(0);
        inventoryRepo.save(inventory);
        return inventory.getId();
    }
    @Override
    public List<InventoryDto> getAllByUser(Long userId) {
        List<InventoryDto> inventoriesDTO = new ArrayList<InventoryDto>();
        List<InventoryUser> inventories = inventoryUserRepo.findByUserId(userId);
        List<Inventory> inventories1 = inventories.stream().map(item -> inventoryRepo.findById(item.getUserInventoryKey().getInventoryId()).get()).collect(Collectors.toList());
        inventoriesDTO = Arrays.asList(mapper.map(inventories1, InventoryDto[].class));
        return inventoriesDTO;
    }
    @Override
    public InventoryDto getDetail(Long inventoryId) {
        Inventory inventory =  inventoryRepo.findById(inventoryId).get();
        InventoryDto response = mapper.map(inventory, InventoryDto.class);
        return response;
    }
    @Override
    public List<UserDTOResponse> getAllStaffInInventory(Long inventoryId) {
        List<UserDTOResponse> staffs = new ArrayList<UserDTOResponse> ();
        List<InventoryUser> inventories = inventoryUserRepo.findByInventoryId(inventoryId);
        inventories.stream().map(item -> {
            User user = userRepo.findById(item.getUserInventoryKey().getUserId()).get();
            if(!user.getRole().equals("ADMIN")) {
                return user;
            } else {
                return null;
            }
        }).collect(Collectors.toList());
        return staffs;
    }

    @Override
    public List<InventoryDto> getByStoreId(Long storeId) {
        List<Inventory> inventories = inventoryRepo.findByStoreId(storeId);
        List<InventoryDto> response = Arrays.asList(mapper.map(inventories, InventoryDto[].class));
        return response;
    }
}
