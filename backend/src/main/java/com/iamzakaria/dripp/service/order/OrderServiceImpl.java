package com.iamzakaria.dripp.service.order;

import com.iamzakaria.dripp.dao.OrderRepository;
import com.iamzakaria.dripp.dto.OrderDTO;
import com.iamzakaria.dripp.entity.Order;
import com.iamzakaria.dripp.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderDTO(
                        order.getId(),
                        order.getCreatedAt(),
                        order.getPhoneNumber(),
                        order.getTotalAmount(),
                        order.getTotalQuantity(),
                        order.getStatus().name(),
                        order.getOrderTrackingNumber(),
                        order.getUser().getFirstName(),
                        order.getUser().getLastName(),
                        order.getUser().getEmail(),
                        order.getShippingAddress() != null ? order.getShippingAddress().getId() : null,
                        order.getShippingAddress() != null ? order.getShippingAddress().getLineAddress():null,
                        List.of(order.getOrderItems().toArray(new OrderItem[0]))
                ))
                .collect(Collectors.toList());
    }

}
