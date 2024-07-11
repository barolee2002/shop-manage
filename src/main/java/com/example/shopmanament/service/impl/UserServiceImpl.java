package com.example.shopmanament.service.impl;

import com.example.shopmanament.config.security.JwtService;
import com.example.shopmanament.dto.*;
import com.example.shopmanament.dto.request.LoginRequest;
import com.example.shopmanament.dto.request.UserDTORequest;
import com.example.shopmanament.dto.response.LoginResponse;
import com.example.shopmanament.dto.response.UserDTOResponse;
import com.example.shopmanament.entity.InventoryUser;
import com.example.shopmanament.entity.User;
import com.example.shopmanament.entity.UserInventoryKey;
import com.example.shopmanament.exception.AppException;
import com.example.shopmanament.exception.Errors;
import com.example.shopmanament.repository.InventoryUserRepository;
import com.example.shopmanament.repository.UserRepository;
import com.example.shopmanament.service.ActionHistoryService;
import com.example.shopmanament.service.UserService;
import com.example.shopmanament.utils.Utils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final InventoryUserRepository inventoryUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JavaMailSender mailSender;

    private final ActionHistoryService actionHistoryService;
    private final ModelMapper mapper = new ModelMapper();
    @Value("${expire.time}")
    private long expireTime;
    private String getCode(Long initValue) {
        String response = "NV" + String.format("%06d", initValue);
        Optional<User> entity = userRepo.findByCode(response);
        if(entity.isEmpty()) {
            return response;
        }
        return getCode(initValue+1);
    }
    @Override
    public UserDTOResponse addUser(UserDTORequest userDTO) {
        if( Utils.isEmptyOrNull(userDTO.getUsername()) || Utils.isEmptyOrNull(userDTO.getPassword()))
            throw new AppException(Errors.INVALID_DATA);
        if(userRepo.findFirstByUsername(userDTO.getUsername()).isPresent()){
            throw new AppException(Errors.EXIST_USER);
        }
        Date current = new Date();
        User user = User.builder()
                .code(userDTO.getCode())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .address(userDTO.getAddress())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(userDTO.getRole())
                .status(1)
                .confirmToken(UUID.randomUUID().toString())
                .isAuthentication(0)
                .build();
        user = userRepo.save(user);
        user.setStoreId(user.getId());
        user = userRepo.save(user);
        sendConfirmationEmail(user);
        return mapper.map(user, UserDTOResponse.class);
    }

    //thiếu thêm nhân viên vào kho
    @Override
    @Transactional
    public UserDTOResponse addStaff(UserDTORequest staff) {
        if( Utils.isEmptyOrNull(staff.getUsername()) || Utils.isEmptyOrNull(staff.getPassword()))
            throw new AppException(Errors.INVALID_DATA);
        if(userRepo.findFirstByUsername(staff.getUsername()).isPresent()){
            throw new AppException(Errors.EXIST_USER);
        }
        Long count = userRepo.countAllByStoreId(staff.getStoreId());
        User user = User.builder()
                .code(staff.getCode())
                .phone(staff.getPhone())
                .email(staff.getEmail())
                .name(staff.getName())
                .username(staff.getUsername())
                .address(staff.getAddress())
                .password(passwordEncoder.encode(staff.getPassword()))
                .role(staff.getRole())
                .storeId(staff.getStoreId())
                .status(1)
                .build();
        if(staff.getCode() == null || staff.getCode() == "") {
            user.setCode(getCode((count)));
        }
        user = userRepo.save(user);
        CookieDto cookieDto = new CookieDto();
        cookieDto.setStoreId(user.getStoreId());
        cookieDto.setUserId(staff.getStoreId());
        actionHistoryService.create(cookieDto, "Tạo nhân viên mã: " +user.getCode());
        if(staff.getInventoryId() != null && staff.getInventoryId() != 0) {
            InventoryUser inventoryUser = new InventoryUser();
            inventoryUser.setUserInventoryKey(new UserInventoryKey(user.getId(), staff.getInventoryId()));
            inventoryUserRepo.save(inventoryUser);
        }

        user = userRepo.save(user);
        sendConfirmationEmail(user);
        return mapper.map(user, UserDTOResponse.class);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        authenticationManager.authenticate(token);
        String jwt = jwtService.generateToken(loginRequest.getUsername());
        Optional<User> userOptional = userRepo.findByUsername(loginRequest.getUsername());
        CookieDto cookieDto = new CookieDto();
        cookieDto.setStoreId(userOptional.get().getStoreId());
        cookieDto.setUserId(userOptional.get().getId());
        actionHistoryService.create(cookieDto, "Đăng nhập vào hệ thống");
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
    public UserDTOResponse update(UserDTORequest userDTO, Long userId) throws AppException {
        Optional<User> userOptional = userRepo.findById(userId);
        if ( userOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        User user = userOptional.get();
        user.setUsername(Utils.isEmptyOrNull(userDTO.getUsername()) ? userDTO.getUsername() : user.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setRole(userDTO.getRole());

        return mapper.map(userRepo.save(user), UserDTOResponse.class);
    }

    @Override
    public UserDTOResponse getInfo(Long userId) throws AppException {
        Optional<User> userOptional = userRepo.findById(userId);
        if(userOptional.isEmpty() || userOptional.get().getStatus() == 0)
            throw new AppException(Errors.NOT_FOUND);
        UserDTOResponse response = mapper.map(userOptional.get(), UserDTOResponse.class);
        response.setActionHistories(actionHistoryService.getByUser(userId));
        return response;
    }

    @Override
    public List<UserDTOResponse> getAll(Long storeId) {
        List<User> entities = userRepo.findByStoreId(storeId);
        return Arrays.asList(mapper.map(entities, UserDTOResponse[].class));
    }
    @Override
    public Long logout(CookieDto cookieDto) {
        actionHistoryService.create(cookieDto, "Đăng xuất khỏi hệ thống");
        return cookieDto.getUserId();
    }
    @Override
    public BasePage<UserDTOResponse> getAllPage(Long storeId, String searchString, String role, Integer page, Integer pageSize) throws AppException {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<User> userPage = userRepo.getAll(storeId,searchString,role, pageable);
        List<UserDTOResponse> response = userPage.stream().map(user -> mapper.map(user, UserDTOResponse.class)).collect(Collectors.toList());
        BasePage<UserDTOResponse> dataPage = new BasePage<>();
        MetaData metaData =  new MetaData();
        metaData.setElements(userPage.getNumberOfElements());
        metaData.setTotalElements(userPage.getTotalElements());
        metaData.setTotalPages(userPage.getTotalPages());
        dataPage.setMetaData(metaData);
        dataPage.setData(response);
        return dataPage;
    }
    @Override
    public Integer sendConfirmationEmail(@NotNull User user) throws AppException  {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("bao29102002@gmail.com");
        mailMessage.setText("Vui lòng click link sau để xác nhận tài khoản của bạn : "
                + "http://localhost:3000/confirm-account?token=" + user.getConfirmToken());

        mailSender.send(mailMessage);
        return 1;
    }
    @Override
    public Integer confirmUser(String confirmationToken) {
        User user = userRepo.findByConfirmToken(confirmationToken);
        if (user != null) {
            user.setIsAuthentication(1);
            userRepo.save(user);
        }
        return 1;
    }
    public List<Date> getDateArrayFromStartOfMonth() throws IOException  {
        List<Date> dateList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startOfMonth = calendar.getTime();

        while (!calendar.getTime().after(today)) {
            dateList.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dateList;
    }

    @Override
    public void getWorkPoints(Long storeId, String time, HttpServletResponse response) throws IOException {
        List<User> staffs = userRepo.findByStoreId(storeId);
        List<WorkPointUser> responseList = staffs.stream().map(staff -> {
            List<WorkPointDto> workPointDtoList = actionHistoryService.getWorkPointsUser(staff.getId(), time);
            UserDTOResponse userDTOResponse = mapper.map(staff, UserDTOResponse.class);
            return new WorkPointUser(userDTOResponse, workPointDtoList);
        }).collect(Collectors.toList());
        System.out.println(responseList);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bảng chấm công");

        // Create header row
        int cellNum = 1;
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Tên nhân viên");
        for (WorkPointUser workPointUser : responseList) {
            for (WorkPointDto workPointDto : workPointUser.getWorkPointDtoList()) {
                headerRow.createCell(cellNum).setCellValue(workPointDto.getDate());
                cellNum++;
            }
            break; // Only do this for the first user, since the dates will be the same for all users
        }

        // Fill data rows
        int rowNum = 1;
        for (WorkPointUser workPointUser : responseList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(workPointUser.getUser().getName());

            int userCol = 1;
            for (WorkPointDto workPointDto : workPointUser.getWorkPointDtoList()) {
                String login = "";
                String logout = "";
                if(workPointDto.getLogin() != null || workPointDto.getLogin()!= "") {
                    login = workPointDto.getLogin();
                }
                if(workPointDto.getLogout() != null || workPointDto.getLogout()!= "") {
                    logout = workPointDto.getLogout();
                }
                String workPoint = login + " - " + logout;
                row.createCell(userCol).setCellValue(workPoint);
                userCol++;
            }
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ChamCong.xlsx");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
        } finally {
            workbook.close();
        }
    }

}
