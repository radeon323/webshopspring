package com.luxoft.oleksandr_shevchenko.webshop.dao.jdbc;

import com.luxoft.oleksandr_shevchenko.webshop.dao.ProductDao;
import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    static String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
    static String jdbcUser = "postgres";
    static String jdbcPass = "postgres";
    static String tableName = "products";

    private static final String ADD = "INSERT INTO products (name, price, creation_date) VALUES (?, ?, ?);";
    private static final String FIND_ALL_SQL = "SELECT id, name, price, creation_date FROM products;";
    private static final String DELETE_BY_ID = "DELETE FROM products WHERE id = ?;";
    private static final String UPDATE_BY_ID = "UPDATE products SET name = ?, price = ? WHERE id = ?;";
    private static final String FIND_BY_ID = "SELECT * FROM products WHERE id = ?";

    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    protected static Connection connect() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }

    @Override
    public List<Product> findAll() {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery();) {

            List<Product> products = Collections.synchronizedList(new ArrayList<>());
            while(resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void add(Product product) {
        try (Connection connection = connect();) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setTimestamp(3, product.getCreationDate());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void remove(int id) {
        try (Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void edit(Product product) {
        try (Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product prFindById(int id) {
        try (Connection connection = connect();) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int pId = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    double price = resultSet.getDouble(3);
                    Timestamp creationDate = resultSet.getTimestamp(4);
                    Product product = Product.builder().
                            id(pId)
                            .name(name)
                            .price(price)
                            .creationDate(creationDate)
                            .build();
                    return product;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

}
