package com.example.parkingwebapp.controller;

import com.example.parkingwebapp.models.Place;
import com.example.parkingwebapp.models.Role;
import com.example.parkingwebapp.repository.PlaceRepository;
import com.example.parkingwebapp.repository.RoleRepository;
import com.example.parkingwebapp.service.CarUserService;
import com.example.parkingwebapp.service.PlaceUserService;
import com.example.parkingwebapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PlaceUserService placeService;
    @Autowired
    private CarUserService carService;

    @GetMapping("/delete_user")
    public String delete(Model model){
        return "deleteUser";
    }

    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam String userName){
        userService.setStatus(userName, false);
        placeService.removeAllPlacesUser(userName);
        carService.removeAllCarsUser(userName);
        return "redirect:/data";
    }

    @GetMapping("/edit_roles")
    public String setRoles(Model model){
        Iterable<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "editRoles";
    }

    @PostMapping("/edit_roles")
    public String addUserRole(@RequestParam String userName, @RequestParam String roles, @RequestParam String choice){
        Role role = roleRepository.findByName(roles);
        List<Role> roleList = userService.getUserRoles(userName);
        if (choice.equals("1")) {
            if (!roleList.contains(role)) {
                userService.addRoleToUser(userName, role);
            }
        } else if (choice.equals("0")){
            if (roleList.contains(role)) {
                userService.removeRoleToUser(userName, role);
            }
        }
        return "redirect:/data";
    }
}
