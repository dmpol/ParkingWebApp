package com.example.parkingwebapp.controller;

import com.example.parkingwebapp.Facade.IAuthenticationFacade;
import com.example.parkingwebapp.models.Place;
import com.example.parkingwebapp.service.PlaceUserService;
import com.example.parkingwebapp.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PlaceController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PlaceUserService placeService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/edit_place")
    public String editPlace(Model model){
        return "editPlace";
    }

    @PostMapping("/edit_place")
    public String editUserPlace(@RequestParam String numberPlace, @RequestParam String choice){
        Authentication authentication = authenticationFacade.getAuthentication();
        Place place = new Place();
        place.setNumberPlace(numberPlace);
        place.setStatusPlace(true);
        if (choice.equals("1")) {
            placeService.addPlaceUser(authentication.getName(), place);
        } else if (choice.equals("0")) {
            placeService.removePlaceUser(authentication.getName(), place);
        }
        return "redirect:/me";
    }

}
