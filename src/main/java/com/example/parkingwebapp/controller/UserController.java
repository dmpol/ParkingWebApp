package com.example.parkingwebapp.controller;

import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller @RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserServiceImpl userService;

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
        return "redirect:/data";
    }
}
