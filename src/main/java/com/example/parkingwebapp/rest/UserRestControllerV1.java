package com.example.parkingwebapp.rest;

import com.example.parkingwebapp.models.User;
import com.example.parkingwebapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/users/")
public class UserRestControllerV1 {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("username") String userName) {
        if (userName == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUser(userName);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveUser(@RequestBody @Validated User user) {
        HttpHeaders headers = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.saveUser(user);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody @Validated User user, UriComponentsBuilder builder) {
        HttpHeaders headers = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.saveUser(user);
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "{username}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUser(@PathVariable("username") String userName) {
        User user = userService.getUser(userName);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
