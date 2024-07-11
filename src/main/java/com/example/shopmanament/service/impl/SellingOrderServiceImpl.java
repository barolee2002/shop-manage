package com.example.shopmanament.service.impl;

import com.example.shopmanament.dto.*;
import com.example.shopmanament.dto.response.UserDTOResponse;
import com.example.shopmanament.entity.SellingOrder;
import com.example.shopmanament.entity.User;
import com.example.shopmanament.repository.SellingOrderRepository;
import com.example.shopmanament.repository.UserRepository;
import com.example.shopmanament.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellingOrderServiceImpl implements SellingOrderService {
    private final SellingOrderRepository sellingOrderRepo;
    private final OderDetailService detailService;
    private final CustomerService customerService;
    private final UserService userService;
    private final UserRepository userRepo;
    private final ProductInventoryService productInventoryService;
    private final ActionHistoryService actionHistoryService;
    private final InventoryService inventoryService;
    private final ModelMapper mapper = new ModelMapper();
    private String getCode(Long initialValue) {
        String response = "BH" + String.format("%06d", initialValue);
        Optional<SellingOrder> entity = sellingOrderRepo.findByCode(response);
        if(entity.isEmpty()) {
            return response;
        }
        return getCode(initialValue+1);
    }
    public BigDecimal totalOrderValueByCustomer(Long customerId) {
        List<SellingOrder> entities = sellingOrderRepo.findByCustomerId(customerId);
        return entities.stream()
                .map(SellingOrder::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Override
    @Transactional
    public SellingOrderDTO create(CookieDto cookieDto,SellingOrderDTO orderDTO) {
        Date current = new Date();
        SellingOrder sellingOrder = mapper.map(orderDTO, SellingOrder.class);
        sellingOrder.setCustomerId(orderDTO.getCustomer().getId());
        Long count = sellingOrderRepo.countByStoreId(orderDTO.getId());
        if(orderDTO.getCode() == null || orderDTO.getCode().equals("")) {
            sellingOrder.setCode(getCode(count));
        }
        sellingOrder.setUserId(orderDTO.getStaff().getId());
        sellingOrder.setCreateAt(current);
        sellingOrder.setInventoryId(orderDTO.getInventory().getId());
        sellingOrder.setStoreId(orderDTO.getStoreId());
        sellingOrder=sellingOrderRepo.save(sellingOrder);
        SellingOrder finalSellingOrder = sellingOrder;
        List<OrderDetailDTO> details =orderDTO.getDetails().stream().map(item -> {
            productInventoryService.updateQuantity(finalSellingOrder.getInventoryId(), item.getProduct().getId(), item.getQuantity());
            return detailService.create(item, finalSellingOrder.getId());
        }).collect(Collectors.toList());
        actionHistoryService.create(cookieDto, "Tạo đơn bán hàng: " +sellingOrder.getCode());
        SellingOrderDTO sellingOrderDTO = new SellingOrderDTO();
        sellingOrderDTO = mapper.map(sellingOrder,SellingOrderDTO.class);
        sellingOrderDTO.setDetails(details);
        User staff = new User();
        staff = userRepo.findById(sellingOrder.getUserId()).get();
        sellingOrderDTO.setStaff(mapper.map(staff, UserDTOResponse.class));

        return sellingOrderDTO;
    }

    @Override
    public BasePage<SellingOrderDTO> getAll(Long storeId,String searchString, Long staffId, Long inventoryId, String sellFromTime, String sellToTime, String paymentType, BigDecimal fromTotal, BigDecimal toTotal, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<SellingOrder> sellingOrderPage = sellingOrderRepo.getAll(storeId,staffId,searchString, inventoryId, sellFromTime, sellToTime, paymentType, fromTotal,toTotal, pageable);
        List<SellingOrder> entities = sellingOrderPage.get().collect(Collectors.toList());
        List<SellingOrderDTO> dtos= entities.stream().map(entity -> {
            SellingOrderDTO dto = mapper.map(entity, SellingOrderDTO.class);
            dto.setStaff(userService.getInfo(entity.getUserId()));
            dto.setInventory(inventoryService.getDetail(entity.getInventoryId()));
            dto.setCustomer(customerService.getDetail(entity.getCustomerId()));
            return dto;
        }).collect(Collectors.toList());
        BasePage<SellingOrderDTO> dataPage = new BasePage<>();
        MetaData metaData =  new MetaData();
        metaData.setTotalElements(sellingOrderPage.getTotalElements());
        metaData.setTotalPages(sellingOrderPage.getTotalPages());
        metaData.setElements(sellingOrderPage.getNumberOfElements());
        dataPage.setMetaData(metaData);
        dataPage.setData(dtos);
        return dataPage;
    }
    @Override
    public SellingOrderDTO getDetail(Long orderId) {
        Optional<SellingOrder> sellingOrder = sellingOrderRepo.findById(orderId);
        SellingOrderDTO response = mapper.map(sellingOrder, SellingOrderDTO.class);
        response.setStaff(userService.getInfo(sellingOrder.get().getUserId()));
        response.setInventory(inventoryService.getDetail(sellingOrder.get().getInventoryId()));
        response.setCustomer(customerService.getDetail(sellingOrder.get().getCustomerId()));
        response.setDetails(detailService.getByOrderId(orderId));
        return response;
    }

    @Override
    public BasePage<SellingOrderDTO> getAllOrderByCustomer(Long customerId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<SellingOrder> sellingOrderPage = sellingOrderRepo.findByCustomerId(customerId, pageable);
        List<SellingOrder> entities = sellingOrderPage.get().collect(Collectors.toList());
        List<SellingOrderDTO> dtos= entities.stream().map(entity -> {
            SellingOrderDTO dto = mapper.map(entity, SellingOrderDTO.class);
            dto.setStaff(userService.getInfo(entity.getUserId()));
            if(entity.getInventoryId() != null && !entity.getInventoryId().equals(BigDecimal.valueOf(0))) {
                dto.setInventory(inventoryService.getDetail(entity.getInventoryId()));
            }
            dto.setCustomer(customerService.getDetail(entity.getCustomerId()));
            return dto;
        }).collect(Collectors.toList());
        BasePage<SellingOrderDTO> dataPage = new BasePage<>();
        MetaData metaData =  new MetaData();
        metaData.setTotalElements(sellingOrderPage.getTotalElements());
        metaData.setTotalPages(sellingOrderPage.getTotalPages());
        metaData.setElements(sellingOrderPage.getNumberOfElements());
        dataPage.setMetaData(metaData);
        dataPage.setData(dtos);
        return dataPage;
    }
    @Override
    public SellingStatisticDto getStatistic(Long storeId, Long inventoryId, String time) {
        Date fromTime = new Date();
        Date toTime = new Date();
        if(time.equals("TODAY")) {
            fromTime = new Date();
        }
        if(time.equals("WEEK")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fromTime);
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
            fromTime = calendar.getTime();
        }
        if(time.equals("MONTH")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fromTime);
            calendar.add(Calendar.MONTH, -1);
            fromTime = calendar.getTime();
        }
        if(time.equals("THREE_MONTH")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fromTime);
            calendar.add(Calendar.MONTH, -3);
            fromTime = calendar.getTime();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fromDate = dateFormat.format(fromTime);
        String toDate = dateFormat.format(toTime);

        List<SellingOrder> results = sellingOrderRepo.getByFilter(storeId,inventoryId,fromDate,toDate);
        SellingStatisticDto response = new SellingStatisticDto();
        BigDecimal totalOrderValue = results.stream().map(result -> {
            return result.getTotal();
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalOrderMoney = results.stream().map(result -> {
            return detailService.totalOrderMoney(result.getId(), inventoryId);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setTotalOrder(results.size());
        response.setTotalOrderValue(totalOrderValue);
        response.setTotalMoney(totalOrderMoney);
        return response;
    }
    public List<java.sql.Date> getDateArray(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date fromTime = new Date(calendar.getTimeInMillis());
        Date toTime = new Date(System.currentTimeMillis());

        switch (time) {
            case "TODAY":
                // fromTime is already set to today
                break;
            case "WEEK":
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                fromTime = calendar.getTime();
                break;
            case "MONTH":
                calendar.add(Calendar.MONTH, -1);
                fromTime = calendar.getTime();
                break;
            case "THREE_MONTH":
                calendar.add(Calendar.MONTH, -3);
                fromTime = calendar.getTime();
                break;
        }

        List<java.sql.Date> dateList = new ArrayList<>();
        calendar.setTime(fromTime);
        while (!calendar.getTime().after(toTime)) {
            dateList.add(new java.sql.Date(calendar.getTimeInMillis()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return dateList;
    }
    @Override
    public List<SellingTimeStatistic> getSellingStatistic(Long storeId, Long inventoryId, String time) {
        List<java.sql.Date> arrayDate = getDateArray(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<SellingTimeStatistic> response = arrayDate.stream().map(date -> {
            // Zero out the time part
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date formattedDate = cal.getTime();

            String day = dateFormat.format(formattedDate);
            List<Object[]> queryResponse = sellingOrderRepo.getByDay(day, storeId, inventoryId);

            if (queryResponse == null || queryResponse.isEmpty()) {
                // Handle empty results by creating an empty statistic for that day
                return new SellingTimeStatistic(formattedDate, BigDecimal.ZERO);
            } else {
                Object[] result = queryResponse.get(0); // Get the first (and likely only) result
                return new SellingTimeStatistic((Date) result[0], (BigDecimal) result[1]);
            }
        }).collect(Collectors.toList());

        return response;
    }

}
