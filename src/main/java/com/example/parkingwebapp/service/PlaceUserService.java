package com.example.parkingwebapp.service;

import com.example.parkingwebapp.models.Place;
import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PlaceUserService {

    private final PlaceRepository placeRepository;
    @Autowired
    private final UserServiceImpl userService;

    public boolean removePlaceUser(String userName, Place place) {
        log.info("IN PlaceUserService removePlaceUser");

        User user = userService.getUser(userName);
        if (user != null) {
            List<Place> userPlacesList = user.getPlaces();
            List<Place> listPlace = userPlacesList.stream()
                    .filter(place1 -> place1.isStatusPlace() == place.isStatusPlace())
                    .filter(place1 -> place1.getNumberPlace().equals(place.getNumberPlace()))
                    .collect(Collectors.toList());
            if (!listPlace.isEmpty()) {
                Place place2 = listPlace.get(0);
                user.removePlace(place2);
                log.info("IN PlaceUserService Place - {} - removed to User - {}!", place.getNumberPlace(), userName);
                return true;
            }
            log.info("IN PlaceUserService removePlaceUser Place - {} - not found at the user - {}!", place.getNumberPlace(), userName);
            return false;
        }
        log.info("IN PlaceUserService removePlaceUser - {} - not found!", userName);
        return false;
    }

    public boolean addPlaceUser(String userName, Place place) {
        log.info("IN PlaceUserService addPlaceUser");

        User user = userService.getUser(userName);
        if (user != null) {
            if (!checkingPlaceInDB(place)) {
                user.addPlace(place);
                placeRepository.save(place);
                log.info("IN PlaceUserService Place - {} - added to User - {}!", place.getNumberPlace(), userName);
                return true;
            }
            log.info("IN PlaceUserService addPlaceUser Place - {} - already exists for the user - {}", place.getNumberPlace(), userName);
            return false;

        }
        log.info("IN PlaceUserService addPlaceUser - {} - not found!", userName);
        return false;
    }

    public boolean checkingPlaceInDB(Place place) {
        log.info("IN PlaceUserService checkingPlaceInDB");
        List<Place> placeList = placeRepository.findAll();
        return placeList.stream()
                .filter(place1 -> place1.isStatusPlace() == (place.isStatusPlace()))
                .anyMatch(place1 -> place1.getNumberPlace().equals(place.getNumberPlace()));
    }

    public List<Place> getAllValidPlaceInDB() {
        log.info("IN PlaceUserService getAllValidPlaceInDB");
        List<Place> placeList = placeRepository.findAll();
        return placeList.stream()
                .filter(place -> place.isStatusPlace() == true)
                .collect(Collectors.toList());
    }

    public List<Place> getAllValidPlacesUser(String userName) {
        log.info("IN PlaceUserService getAllValidPlacesUser");
        User user = userService.getUser(userName);
        return user.getPlaces().stream()
                .filter(place -> place.isStatusPlace() == true)
                .collect(Collectors.toList());
    }

    public boolean removeAllPlacesUser(String userName) {
        log.info("IN PlaceUserService removeAllPlacesUser");
        List<Place> places = getAllValidPlacesUser(userName);
        if (!places.isEmpty()) {
            for (Place p : places) {
                removePlaceUser(userName, p);
            }
            return true;
        }
        return false;
    }
}
