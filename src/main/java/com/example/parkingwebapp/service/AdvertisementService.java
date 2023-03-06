package com.example.parkingwebapp.service;

import com.example.parkingwebapp.models.Advertisement;
import com.example.parkingwebapp.models.Place;
import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    @Autowired
    private UserServiceImpl userService;

    public List<Advertisement> getAllValidAds() {
        log.info("IN AdvertisementService getAllValidAds");
        return advertisementRepository.findAll().stream()
                .filter(advertisement -> advertisement.isStatusAdvertisement() == true)
                .collect(Collectors.toList());
    }

    public List<Advertisement> getAllFreeValidOfferAds() {
        log.info("IN AdvertisementService getAllFreeValidOfferAds");
        return advertisementRepository.findAll().stream()
                .filter(advertisement -> advertisement.isStatusAdvertisement() == true)
                .filter(advertisement -> advertisement.isOffer() == true)
                .filter(advertisement -> advertisement.isApproval() == false)
                .collect(Collectors.toList());
    }

    public Advertisement getAdsId(Long id) {
        List<Advertisement> adsList = getAllFreeValidAds().stream()
                .filter(place -> place.getId()==id)
                .collect(Collectors.toList());
        if (!adsList.isEmpty()) {
            return adsList.get(0);
        }
        return null;
    }

    public List<Advertisement> getAllFreeValidDemandAds() {
        log.info("IN AdvertisementService getAllFreeValidDemandAds");
        return advertisementRepository.findAll().stream()
                .filter(advertisement -> advertisement.isStatusAdvertisement() == true)
                .filter(advertisement -> advertisement.isOffer() == false)
                .filter(advertisement -> advertisement.isApproval() == false)
                .collect(Collectors.toList());
    }

    public List<Advertisement> getAllFreeValidAds() {
        log.info("IN AdvertisementService getAllFreeValidAds");
        return advertisementRepository.findAll().stream()
                .filter(advertisement -> advertisement.isStatusAdvertisement() == true)
                .filter(advertisement -> advertisement.isApproval() == false)
                .collect(Collectors.toList());
    }

    public List<Advertisement> getAllBusyValidAds() {

        log.info("IN AdvertisementService getAllBusyValidAds");
        return advertisementRepository.findAll().stream()
                .filter(advertisement -> advertisement.isStatusAdvertisement() == true)
                .filter(advertisement -> advertisement.isApproval() == true)
                .collect(Collectors.toList());
    }

    public List<Advertisement> getAdsFromDB(Advertisement ads) {
        log.info("IN AdvertisementService getAdsFromDB");
        List<Advertisement> adsList = getAllValidAds();
        return  adsList.stream()
                .filter(advertisement -> advertisement.equals(ads))
                .collect(Collectors.toList());
    }

    public boolean createAds(String userName, Advertisement ads) {
        log.info("IN AdvertisementService createAds");
        User user = userService.getUser(userName);
        if (user != null) {
            advertisementRepository.save(ads);
            List<Advertisement> adsList = getAdsFromDB(ads);
            if (!adsList.isEmpty()){
                user.getAdvertisements().add(ads);
                Advertisement advertisement = adsList.get(0);
                advertisement.getUsers().add(user);
            }
            log.info("IN AdvertisementService createAds - ads has been added to the user - {}", userName);
            return true;
        }
        return false;
    }

    public void addAgreeInAds(String userName, String id, String registrationNumber) {
        log.info("IN AdvertisementService addAgreeInAds");
        Advertisement ads = getAdsId(Long.valueOf(id));
        ads.setRegistrationNumberCar(registrationNumber);
        ads.setApproval(true);
        User user = userService.getUser(userName);
        user.getAdvertisements().add(ads);
        ads.getUsers().add(user);
    }

    public List<Advertisement> getAllValidAdsUser(String userName) {
        log.info("IN AdvertisementService getAllValidAdsUser");
        User user = userService.getUser(userName);
        return user.getAdvertisements().stream()
                .filter(advertisement -> advertisement.isStatusAdvertisement() == true)
                .filter(advertisement -> advertisement.isApproval() == true)
                .collect(Collectors.toList());

    }

    public Date convectStringToDate(String date) {
        String d = date.replaceAll("T", " ");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(d,dtf);

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public String convetDateToString(Date date) {
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dtf.format(localDateTime);
    }

}
