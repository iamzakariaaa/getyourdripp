package com.iamzakaria.dripp.dao;

import com.iamzakaria.dripp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
