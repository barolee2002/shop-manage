package com.example.clothes.service.impl;

import com.example.clothes.dto.InventoryDto;
import com.example.clothes.dto.request.InventoryDTORequest;
import com.example.clothes.dto.response.UserDTOResponse;
import com.example.clothes.entity.Inventory;
import com.example.clothes.entity.InventoryUser;
import com.example.clothes.entity.User;
import com.example.clothes.entity.UserInventoryKey;
import com.example.clothes.repository.InventoryRepository;
import com.example.clothes.repository.InventoryUserRepository;
import com.example.clothes.repository.UserRepository;
import com.example.clothes.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepo;
    private final InventoryUserRepository inventoryUserRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public InventoryDto addInventory(Long userId, InventoryDTORequest inventoryDTORequest) {
        Inventory newInventory = mapper.map(inventoryDTORequest, Inventory.class);
        Long count = inventoryUserRepo.countAllByUserId(userId);
        newInventory.setCode("I" + (count + 1));
        newInventory.setStatus(1);
        newInventory = inventoryRepo.save(newInventory);
        InventoryUser newInventoryUser = new InventoryUser();
        newInventoryUser.setUserInventoryKey(new UserInventoryKey(userId,newInventory.getId()));
        inventoryUserRepo.save(newInventoryUser);
        return mapper.map(newInventory, InventoryDto.class);

    }
    @Override
    public InventoryDto update(Long inventoryId, InventoryDTORequest inventoryDTORequest) {
        Inventory oldInventory = inventoryRepo.findById(inventoryId).get();
        oldInventory = mapper.map(inventoryDTORequest, Inventory.class);
        oldInventory = inventoryRepo.save(oldInventory);
        return mapper.map(oldInventory, InventoryDto.class);
    }
    @Override
    public Long delete(Long inventoryId) {
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
