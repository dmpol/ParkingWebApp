package com.example.parkingwebapp.controller;

import com.example.parkingwebapp.models.Role;
import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.repository.RoleRepository;
import com.example.parkingwebapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired

    private UserServiceImpl userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/delete_user")
    public String delete(Model model){
        return "deleteUser";
    }

    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam String userName){
        userService.setStatus(userName, false);
        return "redirect:/data";
    }

    @GetMapping("/set_roles")
    public String setRoles(Model model){
        Iterable<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "setRoles";
    }

    @PostMapping("/set_roles")
    public String addUserRole(@RequestParam String userName, @RequestParam String roles){
        Role role = roleRepository.findByName(roles);
        List<Role> roleList = userService.getUserRoles(userName);
        if(!roleList.contains(role)){
            userService.addRoleToUser(userName, role);
        }
        return "redirect:/data";
    }
}
