package com.luxoft.oleksandr_shevchenko.webshop.service;

import com.luxoft.oleksandr_shevchenko.webshop.dao.UserDao;
import com.luxoft.oleksandr_shevchenko.webshop.entity.User;

import java.sql.SQLException;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add(User user) throws SQLException {
        userDao.add(user);
        System.out.println("Add user " + user.getFirstName() + user.getLastName());
    }

    public void remove(int id) {
        userDao.remove(id);
        System.out.println("Remove user with id " + id);
    }

    public void edit(User user) {
        userDao.edit(user);
    }

    public User prFindById(int id) {
        return userDao.usrFindById(id);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public boolean isUserExist(String email) {
        return userDao.isUserExist(email);
    }
}
