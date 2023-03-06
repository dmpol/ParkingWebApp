package com.example.parkingwebapp.controller;

import com.example.parkingwebapp.facade.IAuthenticationFacade;
import com.example.parkingwebapp.models.Advertisement;
import com.example.parkingwebapp.models.Place;
import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.service.AdvertisementService;
import com.example.parkingwebapp.service.PlaceUserService;
import com.example.parkingwebapp.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/general")
public class AdvertisementController {
    @Autowired
    private AdvertisementService adsService;
    @Autowired
    private PlaceUserService placeUserService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/ads")
    public String getFormAds(Model model){
        Authentication authentication = authenticationFacade.getAuthentication();
        List<Place> placeList = placeUserService.getAllValidPlacesUser(authentication.getName());
        model.addAttribute("places", placeList);
        return "adsForm";
    }
    @PostMapping("/ads")
    public String completedFormAds (@RequestParam String startdate, @RequestParam String enddate,
                                    @RequestParam String places, @RequestParam String comments) {
        Authentication authentication = authenticationFacade.getAuthentication();
        Date dateStart = adsService.convectStringToDate(startdate);
        Date dateEnd = adsService.convectStringToDate(enddate);
        Advertisement advertisement = new Advertisement();
        advertisement.setStartDate(dateStart);
        advertisement.setEndDate(dateEnd);
        advertisement.setNumberPlace(places);
        advertisement.setStatusAdvertisement(true);
        advertisement.setOffer(true);
        advertisement.setText(comments);
        adsService.createAds(authentication.getName(), advertisement);

        return "redirect:/";
    }
}
