package com.iamzakaria.dripp.service.user;

import com.iamzakaria.dripp.entity.User;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(Long id);
    List<User> getAllUsers();
    User addUser(User user);
    void deleteUser(Long id);

}
