package com.example.clothes.service.impl;

import com.example.clothes.config.security.JwtService;
import com.example.clothes.dto.request.LoginRequest;
import com.example.clothes.dto.request.UserDTORequest;
import com.example.clothes.dto.response.LoginResponse;
import com.example.clothes.dto.response.StaffResponse;
import com.example.clothes.dto.response.UserDTOResponse;
import com.example.clothes.entity.InventoryUser;
import com.example.clothes.entity.User;
import com.example.clothes.entity.UserInventoryKey;
import com.example.clothes.exception.AppException;
import com.example.clothes.exception.Errors;
import com.example.clothes.repository.InventoryUserRepository;
import com.example.clothes.repository.UserRepository;
import com.example.clothes.service.UserService;
import com.example.clothes.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final InventoryUserRepository inventoryUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModelMapper mapper = new ModelMapper();
    @Value("${expire.time}")
    private long expireTime;
    @Override
    public UserDTOResponse addUser(UserDTOResponse userDTO) {
        if( Utils.isEmptyOrNull(userDTO.getUsername()) || Utils.isEmptyOrNull(userDTO.getPassword()))
            throw new AppException(Errors.INVALID_DATA);
        if(userRepo.findFirstByUsername(userDTO.getUsername()).isPresent()){
            throw new AppException(Errors.EXIST_USER);
        }
        User user = mapper.map(userDTO, User.class);
        user.setStatus(1);
        user = userRepo.save(user);
        user.setStoreId(user.getId());
        return mapper.map(userRepo.save(user), UserDTOResponse.class);
    }

    //thiếu thêm nhân viên vào kho
    @Override
    public StaffResponse addStaff(StaffResponse staff) {
        if( Utils.isEmptyOrNull(staff.getUsername()) || Utils.isEmptyOrNull(staff.getPassword()))
            throw new AppException(Errors.INVALID_DATA);
        if(userRepo.findFirstByUsername(staff.getUsername()).isPresent()){
            throw new AppException(Errors.EXIST_USER);
        }
        User user = mapper.map(staff, User.class);
        user.setStatus(1);
        Long count = userRepo.countAllByStoreId(staff.getStoreId());
        user.setCode("S" + String.format("%06d", count));
        user = userRepo.save(user);
        InventoryUser inventoryUser = new InventoryUser();
        inventoryUser.setUserInventoryKey(new UserInventoryKey(user.getId(), staff.getInventoryId()));
        inventoryUserRepo.save(inventoryUser);
        return mapper.map(userRepo.save(user), StaffResponse.class);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        authenticationManager.authenticate(token);
        String jwt = jwtService.generateToken(loginRequest.getUsername());
        Optional<User> userOptional = userRepo.findByUsername(loginRequest.getUsername());
        return LoginResponse.builder()
                .username(loginRequest.getUsername())
                .storeId(userOptional.get().getStoreId())
                .name(userOptional.get().getName())
                .token(jwt)
                .expireTime(System.currentTimeMillis() + expireTime)
                .id(userOptional.get().getId())
                .build();
    }

    @Override
    public UserDTOResponse update(UserDTORequest userDTO, Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if ( userOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        User user = userOptional.get();
        user.setUsername(Utils.isEmptyOrNull(userDTO.getUsername()) ? userDTO.getUsername() : user.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setRole(userDTO.getRole());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return mapper.map(userRepo.save(user), UserDTOResponse.class);
    }

    @Override
    public UserDTOResponse getInfo(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if(userOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        return mapper.map(userOptional.get(), UserDTOResponse.class);
    }

}
