package com.example.parkingwebapp.service;

import com.example.parkingwebapp.models.Role;
import com.example.parkingwebapp.models.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    User getUser(String userName);
    List<User> findAll();
    List<Role> addRoleToUser(String userName, Role role);
    List<Role> getUserRoles(String userName);
}
