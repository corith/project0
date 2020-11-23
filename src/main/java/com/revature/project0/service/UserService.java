package com.revature.project0.service;

import com.revature.project0.dao.DatabaseUserDao;
import com.revature.project0.models.User;

import java.util.ArrayList;

public class UserService {
    private final DatabaseUserDao userDao;

    public UserService() {
        this.userDao = new DatabaseUserDao();
    }

    public ArrayList<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User getUser(int id) {
        return userDao.getUser(id);
    }

    public User insertUser(User newUser) {
        return userDao.insertUser(newUser);
    }

    public User updateUser(User user , User newFields) {
        return userDao.updateUser(user , newFields);
    }
}
