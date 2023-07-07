package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserDao userDao;


    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }


    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<User> listUsers() {

        return userDao.getUsers();
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable int userId) {

        User user = userDao.getUserById(userId);
        if ( user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        } else {
            return user;
        }

    }
}
