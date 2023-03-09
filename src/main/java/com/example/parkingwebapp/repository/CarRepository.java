package com.example.parkingwebapp.repository;

import com.example.parkingwebapp.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Override
    List<Car> findAll();
}
