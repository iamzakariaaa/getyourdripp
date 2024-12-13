package com.iamzakaria.dripp.dao;

import com.iamzakaria.dripp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
