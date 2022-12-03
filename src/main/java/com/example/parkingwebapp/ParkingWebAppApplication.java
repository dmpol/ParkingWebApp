package com.example.parkingwebapp;

import com.example.parkingwebapp.models.Role;
import com.example.parkingwebapp.models.RoleEnum;
import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.repository.RoleRepository;
import com.example.parkingwebapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class ParkingWebAppApplication {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(ParkingWebAppApplication.class, args);
    }

    @PostConstruct
    public void init() {
        User user = userService.getUser("admin");
        if(user == null) {
            user = new User();
            user.setUsername("admin");
            user.setPassword("admin");
            user.setStatus(true);
            userService.saveUser(user);

            Role role = new Role();
            role.setName(RoleEnum.ADMIN.name());
            roleRepository.save(role);

            Role roleStaff = new Role();
            roleStaff.setName(RoleEnum.STAFF.name());
            roleRepository.save(roleStaff);

            userService.addRoleToUser("admin", role);
            Role r = roleRepository.findByName(RoleEnum.ADMIN.name());
            List<User> lUser = r.getUsers();
            lUser.add(userService.getUser("admin"));
        }
    }
}
