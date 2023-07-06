package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.RegisterUserDto;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();
    User getUserByUsername(String username);
    User getUserById(int id);
    User createUser(RegisterUserDto user);
}
