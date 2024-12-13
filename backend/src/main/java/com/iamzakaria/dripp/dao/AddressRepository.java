package com.iamzakaria.dripp.dao;

import com.iamzakaria.dripp.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
