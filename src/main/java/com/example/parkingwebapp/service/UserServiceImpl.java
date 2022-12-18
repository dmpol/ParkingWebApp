package com.example.parkingwebapp.service;

import com.example.parkingwebapp.models.Role;
import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.repository.RoleRepository;
import com.example.parkingwebapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("IN UserServiceImpl loadUserByUsername");

        User user = userRepository.findByUsername(username);
        if (user==null) {
            log.info("IN UserServiceImpl loadUserByUsername - user {} - not found!", username);
            throw new UsernameNotFoundException("User not found");
        }

        log.info("IN UserServiceImpl loadUserByUsername - user {} - found!", username);
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority("ROLE_" + role.getName())).toList();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("IN UserServiceImpl saveUser");

        try {
            if (user != null) {
                user.setStatus(true);
                user.setPassword(passwordEncoder().encode(user.getPassword()));
                log.info("IN UserServiceImpl saveUser - user {} - save", user.getUsername());
            } else {
                log.info("IN UserServiceImpl saveUser - user = null");
            }
        } catch (Exception e) {
            log.info("IN UserServiceImpl saveUser - Failed to save user {}", user.getUsername());
        }
        return userRepository.save(user);
    }

    @Override
    public User getUser(String userName) {
        log.info("IN UserServiceImpl getUser");

        User user = userRepository.findByUsername(userName);
        if (user != null){
            log.info("IN UserServiceImpl getUser - {} - found!", userName);
        } else {
            log.info("IN UserServiceImpl getUser - {} - not found!", userName);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        log.info("IN UserServiceImpl findAll");

        List<User> list = userRepository.findAll();
        if (list.isEmpty()){
            log.info("IN UserServiceImpl findAll - list is Empty!");
        } else {
            log.info("IN UserServiceImpl findAll - list is not Empty!");
        }
        return list;
    }

    @Override
    public List<Role> addRoleToUser(String userName, Role role) {
        log.info("IN UserServiceImpl addRoleToUser");

        User byUserName = userRepository.findByUsername(userName);
        if (byUserName != null){
            log.info("IN UserServiceImpl addRoleToUser - {} - found!", userName);
            byUserName.getRoles().add(role);
            log.info("IN UserServiceImpl addRoleToUser - role has been added to the user - {}", userName);
            return byUserName.getRoles();
        } else {
            log.info("IN UserServiceImpl addRoleToUser - {} - not found!", userName);
        }
        return null;
    }

    @Override
    public List<Role> getUserRoles(String userName) {
        log.info("IN UserServiceImpl getUserRoles");

        User byUserName = userRepository.findByUsername(userName);
        if (byUserName != null){
            log.info("IN UserServiceImpl getUserRoles - {} - found!", userName);
            log.info("IN UserServiceImpl getUserRoles - list of roles received!");
            return byUserName.getRoles();
        } else {
            log.info("IN UserServiceImpl getUserRoles - {} - not found!", userName);
        }
        return null;
    }

    @Override
    public boolean setStatus(String userName, boolean status) {
        log.info("IN UserServiceImpl setStatus");

        User user = userRepository.findByUsername(userName);
        if (user != null) {
            log.info("IN UserServiceImpl setStatus - {} - found!", userName);
            user.setStatus(status);
            log.info("IN UserServiceImpl setStatus - User {} - has changed the Status!", userName);
            return true;
        } else {
            log.info("IN UserServiceImpl setStatus - {} - not found!", userName);
        }
        return false;
    }

    public boolean deleteUser(String userName){
        log.info("IN UserServiceImpl deleteUser");

        User user = userRepository.findByUsername(userName);
        if (user != null) {
            userRepository.delete(user);
            log.info("IN UserServiceImpl deleteUser - {} - deleted!", userName);
            return true;
        }
        else {
            log.info("IN UserServiceImpl deleteUser - {} - not found!", userName);
        }
        return false;
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

}
