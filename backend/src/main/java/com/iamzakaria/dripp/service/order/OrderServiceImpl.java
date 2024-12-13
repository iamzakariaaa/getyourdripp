package com.iamzakaria.dripp.service.order;

import com.iamzakaria.dripp.dao.OrderRepository;
import com.iamzakaria.dripp.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

}
