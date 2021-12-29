package com.luxoft.oleksandr_shevchenko.webshop.dao;

import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;
import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    void remove(int id);

    void edit(Product product);

    void add(Product product);

    Product prFindById(int id);
}
