package com.luxoft.oleksandr_shevchenko.webshop.service;

import com.luxoft.oleksandr_shevchenko.webshop.dao.ProductDao;
import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findAll() {
        List<Product> products = productDao.findAll();
        System.out.println("Obtain products: " + products.size());
        return  products;
    }

    public void add(Product product) throws SQLException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        product.setCreationDate(timestamp);
        productDao.add(product);
        System.out.println("Add product " + product.getName());
    }

    public void remove(int id) {
        productDao.remove(id);
        System.out.println("Remove product with id " + id);
    }

    public void edit(Product product) {
        productDao.edit(product);
    }

    public Product prFindById(int id) {
        return productDao.prFindById(id);
    }
}
