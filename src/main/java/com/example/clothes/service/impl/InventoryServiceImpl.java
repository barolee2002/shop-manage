package com.example.clothes.service.impl;

import com.example.clothes.dto.request.InventoryDTORequest;
import com.example.clothes.dto.response.InventoryDTOResponse;
import com.example.clothes.dto.response.UserDTOResponse;
import com.example.clothes.entity.Inventory;
import com.example.clothes.entity.InventoryUser;
import com.example.clothes.entity.User;
import com.example.clothes.repository.InventoryRepository;
import com.example.clothes.repository.InventoryUserRepository;
import com.example.clothes.repository.UserRepository;
import com.example.clothes.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepo;
    private final InventoryUserRepository inventoryUserRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public InventoryDTOResponse addInventory(Long userId, InventoryDTORequest inventoryDTORequest) {
        Inventory newInventory = mapper.map(inventoryDTORequest, Inventory.class);
        Long count = inventoryUserRepo.countAllByUserId(userId);
        newInventory.setCode("I" + (count + 1));
        newInventory.setStatus(1);
        newInventory = inventoryRepo.save(newInventory);
        InventoryUser newInventoryUser = new InventoryUser();
        newInventoryUser.setInventoryId(newInventory.getId());
        newInventoryUser.setUserId(userId);
        inventoryUserRepo.save(newInventoryUser);
        return mapper.map(newInventory, InventoryDTOResponse.class);

    }
    @Override
    public InventoryDTOResponse update(Long inventoryId, InventoryDTORequest inventoryDTORequest) {
        Inventory oldInventory = inventoryRepo.findById(inventoryId).get();
        oldInventory = mapper.map(inventoryDTORequest, Inventory.class);
        oldInventory = inventoryRepo.save(oldInventory);
        return mapper.map(oldInventory, InventoryDTOResponse.class);
    }
    @Override
    public Long delete(Long inventoryId) {
        Inventory inventory = inventoryRepo.findById(inventoryId).get();
        inventory.setStatus(0);
        inventoryRepo.save(inventory);
        return inventory.getId();
    }
    @Override
    public List<InventoryDTOResponse> getAllByUser(Long userId) {
        List<InventoryDTOResponse> inventoriesDTO = new ArrayList<InventoryDTOResponse>();
        List<InventoryUser> inventories = inventoryUserRepo.findByUserId(userId);
        for(InventoryUser inventoryItem :  inventories) {
            Inventory inventory = inventoryRepo.findById(inventoryItem.getInventoryId()).get();
            inventoriesDTO.add(mapper.map(inventory,InventoryDTOResponse.class));
        }
        return inventoriesDTO;
    }
    @Override
    public InventoryDTOResponse getDetail(Long inventoryId) {
        Inventory inventory =  inventoryRepo.findById(inventoryId).get();
        return mapper.map(inventory, InventoryDTOResponse.class);
    }
    @Override
    public List<UserDTOResponse> getAllStaffInInventory(Long inventoryId) {
        List<UserDTOResponse> staffs = new ArrayList<UserDTOResponse> ();
        List<InventoryUser> inventories = inventoryUserRepo.findByInventoryId(inventoryId);
        for(InventoryUser inventoryItem :  inventories) {
            User user = userRepo.findById(inventoryItem.getUserId()).get();
            if(!user.getRole().equals("ADMIN")) {
                staffs.add(mapper.map(user, UserDTOResponse.class));
            }
        }
        return staffs;
    }
}
