package com.example.shopmanament.dto;

import com.example.shopmanament.dto.response.UserDTOResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GoodsReceiptDTO {
    private Long id;
    private String code;
    private SupplierDTO supplier;
    private UserDTOResponse bookingUser;
    private UserDTOResponse confirmUser;
    private InventoryDto inventory;
    private Long storeId;
    private String paymentType;
    private BigDecimal total;
    private Date bookingDate;
    private Integer payStatus;
    private Integer receiptStatus;
    private Date receiptDate;
    private List<GoodsReiceptProductDTO> products;
}
