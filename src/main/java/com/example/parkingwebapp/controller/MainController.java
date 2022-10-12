package com.example.parkingwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class MainController {

    @GetMapping("/")
    public String start(Model model){
        model.addAttribute("title", "Главная страница");
        return "home";
    }
}
