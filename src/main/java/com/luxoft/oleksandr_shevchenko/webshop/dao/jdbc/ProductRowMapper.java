package com.luxoft.oleksandr_shevchenko.webshop.dao.jdbc;

import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        int id  = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price  = resultSet.getDouble("price");
        Timestamp creationDate = resultSet.getTimestamp("creation_date");
        Product product = Product.builder().
                id(id)
                .name(name)
                .price(price)
                .creationDate(creationDate)
                .build();
    return product;
    }
}
