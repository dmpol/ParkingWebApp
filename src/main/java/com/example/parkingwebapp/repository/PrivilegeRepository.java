package com.example.parkingwebapp.repository;

import com.example.parkingwebapp.models.Privilege;
import com.example.parkingwebapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository //extends JpaRepository<Privilege, Long>
{
    //Privilege findByName(String name);
}
