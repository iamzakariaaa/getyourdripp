package com.iamzakaria.dripp.dto;

import com.iamzakaria.dripp.entity.Address;
import com.iamzakaria.dripp.entity.Order;
import com.iamzakaria.dripp.entity.OrderItem;
import com.iamzakaria.dripp.entity.User;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private User user;
    private Address shippingAddress;
    private String phoneNumber;
    private Order order;
    private Set<OrderItem> orderItems;
}
