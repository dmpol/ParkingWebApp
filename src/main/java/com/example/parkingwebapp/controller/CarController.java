package com.example.parkingwebapp.controller;

import com.example.parkingwebapp.facade.IAuthenticationFacade;
import com.example.parkingwebapp.models.Car;
import com.example.parkingwebapp.service.CarUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/general")
public class CarController {
    @Autowired
    public CarUserService carService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/edit_car")
    public String editCar(Model model){
        return "editCar";
    }

    @PostMapping("/edit_car")
    public String editUserCar(@RequestParam String registrationNumber, @RequestParam String choice){
        Authentication authentication = authenticationFacade.getAuthentication();
        Car car = new Car();
        car.setRegistrationNumber(registrationNumber);
        car.setStatusCar(true);
        if (choice.equals("1")) {
            carService.addCarUser(authentication.getName(), car);
        } else if (choice.equals("0")) {
            carService.removeCarUser(authentication.getName(), car);
        }
        return "redirect:/me";
    }

}
