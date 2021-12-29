package com.luxoft.oleksandr_shevchenko.webshop.dao.jdbc;

import com.luxoft.oleksandr_shevchenko.webshop.entity.User;
import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException, NoSuchAlgorithmException {
        int id  = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String gender = resultSet.getString("gender");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String about = resultSet.getString("about");
        int age  = resultSet.getInt("age");
        User user = User.builder().
                id(id)
                .email(email)
                .password(SecurityService.md5(password))
                .gender(gender)
                .firstName(firstName)
                .lastName(lastName)
                .about(about)
                .age(age)
                .build();
        return user;
    }


}
