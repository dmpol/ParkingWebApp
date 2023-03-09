package com.example.parkingwebapp.repository;

import com.example.parkingwebapp.models.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    @Override
    List<Advertisement> findAll();
}
