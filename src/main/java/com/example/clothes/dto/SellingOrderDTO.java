package com.example.clothes.dto;

import com.example.clothes.dto.response.UserDTOResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SellingOrderDTO {
    private Long id;
    private String code;
    private CustomerDTO customerDTO;
    private UserDTOResponse staff;
    private BigDecimal total;
    private Integer status;
    private Date createAt;
    private List<OrderDetailDTO> detailDTOS;
}
