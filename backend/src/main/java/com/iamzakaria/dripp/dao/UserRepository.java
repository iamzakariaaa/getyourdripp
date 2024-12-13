package com.iamzakaria.dripp.dao;

import com.iamzakaria.dripp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
