package com.dao;

import java.util.List;

import com.entities.User;

public interface UserDao {
    public void addUser(User user);

    public List<User> getUserByEmail(String email);

    public List<User> getAllUsers();
}
