package com.iamzakaria.dripp.dao;

import com.iamzakaria.dripp.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
