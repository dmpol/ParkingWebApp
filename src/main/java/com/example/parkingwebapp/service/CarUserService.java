package com.example.parkingwebapp.service;

import com.example.parkingwebapp.models.Car;
import com.example.parkingwebapp.models.Place;
import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CarUserService {

    private final CarRepository carRepository;
    @Autowired
    private final UserServiceImpl userService;

    public boolean addCarUser(String userName, Car car) {
        log.info("IN CarUserService addCarUser");

        User user = userService.getUser(userName);
        if (user != null) {
            if (!checkingCarInDB(car)) {
                user.addCar(car);
                carRepository.save(car);
                log.info("IN CarUserService Car - {} - added to User - {}!", car.getRegistrationNumber(), userName);
                return true;
            }
            log.info("IN CarUserService addCarUser Car - {} - already exists for the user - {}", car.getRegistrationNumber(), userName);
            return false;

        }
        log.info("IN CarUserService addCarUser - {} - not found!", userName);
        return false;
    }

    public boolean removeCarUser(String userName, Car car) {
        log.info("IN CarUserService removeCarUser");

        User user = userService.getUser(userName);
        if (user != null) {
            List<Car> userCarsList = user.getCars();
            List<Car> listCar = userCarsList.stream()
                    .filter(car1 -> car1.isStatusCar() == car.isStatusCar())
                    .filter(car1 -> car1.getRegistrationNumber().equals(car.getRegistrationNumber()))
                    .collect(Collectors.toList());
            if (!listCar.isEmpty()) {
                Car car2 = listCar.get(0);
                user.removeCar(car2);
                log.info("IN CarUserService Car - {} - removed to User - {}!", car.getRegistrationNumber(), userName);
                return true;
            }
            log.info("IN CarUserService removeCarUser Car - {} - not found at the user - {}!", car.getRegistrationNumber(), userName);
            return false;
        }
        log.info("IN CarUserService removeCarUser - {} - not found!", userName);
        return false;
    }

    public boolean checkingCarInDB(Car car) {
        log.info("IN CarUserService checkingCarInDB");
        List<Car> carList = carRepository.findAll();
        return carList.stream()
                .filter(car1 -> car1.isStatusCar() == (car.isStatusCar()))
                .anyMatch(car1 -> car1.getRegistrationNumber().equals(car.getRegistrationNumber()));
    }

    public List<Car> getAllValidCarsUser(String userName) {
        log.info("IN CarUserService getAllValidCarUser");
        User user = userService.getUser(userName);
        return user.getCars().stream()
                .filter(car -> car.isStatusCar() == true)
                .collect(Collectors.toList());
    }

    public boolean removeAllCarsUser(String userName) {
        log.info("IN CarUserService removeAllCarsUser");
        List<Car> cars = getAllValidCarsUser(userName);
        if (!cars.isEmpty()) {
            for (Car c : cars) {
                removeCarUser(userName, c);
            }
            return true;
        }
        return false;
    }

    public List<Car> getAllValidCarsInDB() {
        log.info("IN CarUserService getAllValidCarsInDB");
        List<Car> carList = carRepository.findAll();
        return carList.stream()
                .filter(car -> car.isStatusCar() == true)
                .collect(Collectors.toList());
    }

}
