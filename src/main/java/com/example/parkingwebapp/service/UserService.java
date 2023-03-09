package com.example.parkingwebapp.service;

import com.example.parkingwebapp.models.Role;
import com.example.parkingwebapp.models.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User saveUser(User user);
    User getUser(String userName);
    List<User> findAll();
    Set<Role> addRoleToUser(String userName, Role role);
    Set<Role> getUserRoles(String userName);
    boolean setStatus(String userName, boolean status);
}
