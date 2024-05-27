package com.example.clothes.dto;

import com.example.clothes.dto.response.UserDTOResponse;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NonNull;

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
