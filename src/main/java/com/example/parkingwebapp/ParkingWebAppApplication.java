package com.example.parkingwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ParkingWebAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParkingWebAppApplication.class, args);
    }
}
