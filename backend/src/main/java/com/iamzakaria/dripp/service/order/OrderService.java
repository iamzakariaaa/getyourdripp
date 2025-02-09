package com.iamzakaria.dripp.service.order;

import com.iamzakaria.dripp.dto.OrderDTO;
import com.iamzakaria.dripp.entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();

}
