package com.example.parkingwebapp.controller;

import com.example.parkingwebapp.models.Role;
import com.example.parkingwebapp.models.RoleEnum;
import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.repository.RoleRepository;
import com.example.parkingwebapp.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller @RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/data")
    public String getAllUsers(Model model){
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "usersAll";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUserData(@RequestParam String firstName, @RequestParam String lastName,
                              @RequestParam String username, @RequestParam String password){
        User userFromDb = userService.getUser(username);
        if (userFromDb != null){
            return "login";
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        userService.saveUser(u);

        Role userRole = roleRepository.findByName("USER");
        if(userRole == null) {
            userRole = new Role();
            userRole.setName(RoleEnum.USER.name());
            roleRepository.save(userRole);
        }

        userService.addRoleToUser(username, userRole);
        Role r = roleRepository.findByName("USER");
        List<User> lUser = r.getUsers();
        lUser.add(userService.getUser(username));

        return "redirect:/data";
    }
}
