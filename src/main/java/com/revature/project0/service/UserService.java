package com.revature.project0.service;

import com.revature.project0.dao.DatabaseUserDao;
import com.revature.project0.models.User;

import java.util.ArrayList;

public class UserService {
    private DatabaseUserDao userDao;

    public UserService() {
        this.userDao = new DatabaseUserDao();
    }

    public ArrayList<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User getUser(int id) {
        return userDao.getUser(id);
    }
}
