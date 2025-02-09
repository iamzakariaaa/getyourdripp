package com.iamzakaria.dripp.dto;

import com.iamzakaria.dripp.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private LocalDateTime createdAt;
    private String phoneNumber;
    private BigDecimal totalAmount;
    private int totalQuantity;
    private String status;
    private String orderTrackingNumber;

    // User details
    private String userFirstName;
    private String userLastName;
    private String userEmail;

    // Shipping address
    private Long shippingAddressId;
    private String lineAddress;
    private List<OrderItem> items;

    public OrderDTO(Long id, LocalDateTime createdAt, String phoneNumber, BigDecimal totalAmount, int totalQuantity, String status, String orderTrackingNumber,
                    String userFirstName, String userLastName, String userEmail, Long shippingAddressId,  String lineAddress,List<OrderItem> items) {
        this.id = id;
        this.createdAt = createdAt;
        this.phoneNumber = phoneNumber;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.status = status;
        this.orderTrackingNumber = orderTrackingNumber;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.shippingAddressId = shippingAddressId;
        this.lineAddress = lineAddress;
        this.items = items;
    }
}
