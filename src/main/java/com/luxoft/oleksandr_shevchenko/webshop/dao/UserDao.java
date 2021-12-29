package com.luxoft.oleksandr_shevchenko.webshop.dao;

import com.luxoft.oleksandr_shevchenko.webshop.entity.User;

public interface UserDao {


    void remove(int id);

    void edit(User user);

    void add(User user);

    User usrFindById(int id);

    User findByEmail (String email);

    boolean isUserExist(String email);

}
