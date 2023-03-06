package com.example.parkingwebapp.controller;

import com.example.parkingwebapp.facade.IAuthenticationFacade;
import com.example.parkingwebapp.models.Advertisement;
import com.example.parkingwebapp.models.Car;
import com.example.parkingwebapp.models.Place;
import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.service.AdvertisementService;
import com.example.parkingwebapp.service.CarUserService;
import com.example.parkingwebapp.service.PlaceUserService;
import com.example.parkingwebapp.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/general")
public class dataAdsController {

    @Autowired
    private AdvertisementService adsService;
    @Autowired
    public CarUserService carService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/dataAds")
    public String startDataAds(Model model) {
        List<Advertisement> freeOfferList = adsService.getAllFreeValidOfferAds();
        List<Advertisement> freeDemandList = adsService.getAllFreeValidDemandAds();
        model.addAttribute("freeOfL",freeOfferList);
        model.addAttribute("freeDeL",freeDemandList);

        return "parking";
    }

    @PostMapping("/adsResponseOffer")
    public String responseAdsOffer(@RequestParam Long idAds, @RequestParam String startDate,
                                         @RequestParam String endDate, @RequestParam String numberPlace,
                                         @RequestParam String text, RedirectAttributes attributes) {
        attributes.addAttribute("sDate", startDate);
        attributes.addAttribute("eDate", endDate);
        attributes.addAttribute("numberPlace", numberPlace);
        attributes.addAttribute("q", idAds);
        attributes.addAttribute("text", text);
        return "redirect:/general/adsResponseOfferForm";
    }

    @GetMapping("/adsResponseOfferForm")
    public String responseAdsOfferForm(Model model) {
        Authentication authentication = authenticationFacade.getAuthentication();
        List<Car> carList = carService.getAllValidCarsUser(authentication.getName());
        model.addAttribute("carList", carList);
        return "adsRespOfferForm";
    }

    @PostMapping("/saveAdsResponseOfferForm")
    public String saveResponseAdsOfferForm(@RequestParam String id, @RequestParam String registrationNumber) {
        Authentication authentication = authenticationFacade.getAuthentication();
        adsService.addAgreeInAds(authentication.getName(),id,registrationNumber);
        return "redirect:/me";
    }

    @PostMapping("/adsResponseDemand")
    public String responseAdsDemand(@RequestParam Long idAds, @RequestParam String startDate,
                                    @RequestParam String endDate, @RequestParam String registrationNumberCar,
                                    @RequestParam String text, RedirectAttributes attributes) {
        attributes.addAttribute("sDate", startDate);
        attributes.addAttribute("eDate", endDate);
        attributes.addAttribute("numberPlace", registrationNumberCar);
        attributes.addAttribute("q", idAds);
        attributes.addAttribute("text", text);
        return "redirect:/me";
    }

    @GetMapping("/adsResponseOfferFormSave")
    public String responseAdsOfferFormSave(Model model) {
        return "redirect:/me";
    }

}
