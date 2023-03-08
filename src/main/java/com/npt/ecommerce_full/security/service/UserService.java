package com.npt.ecommerce_full.security.service;

import com.npt.ecommerce_full.model.Role;
import com.npt.ecommerce_full.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    User getUser(String userName);
    List<User> getUsers();
}
