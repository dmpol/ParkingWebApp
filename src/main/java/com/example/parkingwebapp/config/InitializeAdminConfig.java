package com.example.parkingwebapp.config;

import com.example.parkingwebapp.models.Role;
import com.example.parkingwebapp.models.RoleEnum;
import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.repository.PlaceRepository;
import com.example.parkingwebapp.repository.RoleRepository;
import com.example.parkingwebapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Component
public class InitializeAdminConfig {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @Value("${admin.name}")
    private String name;
    @Value("${admin.password}")
    private String pass;

    @PostConstruct
    public void init() {
        User user = userService.getUser(name);
        if(user == null) {
            user = new User();
            user.setUsername(name);
            user.setPassword(pass);
            userService.saveUser(user);

            Role role = new Role();
            role.setName(RoleEnum.ADMIN.name());
            roleRepository.save(role);

            Role roleStaff = new Role();
            roleStaff.setName(RoleEnum.STAFF.name());
            roleRepository.save(roleStaff);

            userService.addRoleToUser(name, role);
            Role r = roleRepository.findByName(RoleEnum.ADMIN.name());
            Set<User> lUser = r.getUsers();
            lUser.add(userService.getUser(name));
        }
    }
}
