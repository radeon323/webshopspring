package com.luxoft.oleksandr_shevchenko.webshop.dao.jdbc;


import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;
import com.luxoft.oleksandr_shevchenko.webshop.entity.User;
import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;

public class JdbcProcessor {
    static String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
    static String jdbcUser = "postgres";
    static String jdbcPass = "postgres";
    static String tableName = "users";

//    private static final String CREATE_TABLE = "CREATE TABLE products (id SERIAL not NULL, name VARCHAR(50), price VARCHAR(50), creation_date VARCHAR(50))";
    private static final String CREATE_TABLE = "CREATE TABLE users (id SERIAL not NULL, email VARCHAR(50), password VARCHAR(50), gender VARCHAR(50), firstName VARCHAR(50), lastName VARCHAR(50), about TEXT, age INT NOT NULL DEFAULT 0)";
    private static final String ADD = "INSERT INTO users (email, password, gender, firstName, lastName, about, age) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String FIND_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String UPDATE_BY_ID = "UPDATE users SET password = ? WHERE email = ?;";

    protected static Connection connect() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }

    public static void clearTable(String tableName) throws SQLException {
        try (Connection connection = connect();) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM " + tableName + ";");
            System.out.println("Table " + tableName + " was successfully cleared...");
        }
    }

    public static void dropTable(String tableName) throws SQLException {
        try (Connection connection = connect();) {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE " + tableName + ";");
            System.out.println("Table " + tableName + " was successfully dropped...");
        }
    }

    public static void createTable(String tableName) throws SQLException {
        try (Connection connection = connect();) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE);
            preparedStatement.execute();
            System.out.println("Table " + tableName + " was successfully created...");
        }
    }

    public void add(User user) throws SQLException {
        try (Connection connection = connect();) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getGender());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getAbout());
            preparedStatement.setInt(7, user.getAge());
            preparedStatement.executeUpdate();
        }
    }


//    public static String md5(String text) throws NoSuchAlgorithmException {
//        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//        String sole = "j3qq4m3hk8";
//        String txt = text + sole;
//        byte[] bytes = messageDigest.digest(txt.getBytes());
//        return Hex.encodeHexString(bytes);
//    }
public static boolean isUserExist(String email) {
    try (Connection connection = connect();
         PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL)) {
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            if(Objects.equals(email, resultSet.getString("email"))) {
                return true;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException();
    }
    return false;
}


    public static void edit() {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID)) {
            preparedStatement.setString(1, "8cfa2282b17de0a598c010f5f0109e7d");
            preparedStatement.setString(2, "4336399@gmail.com");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
        //createTable(tableName);
        //System.out.println(md5("12345"));
//        try (Connection connection = connect();) {
//            PreparedStatement preparedStatement = connection.prepareStatement(ADD);
//            preparedStatement.setString(1, "dartveider@gmail.com");
//            preparedStatement.setString(2, SecurityService.md5("123456"));
//            preparedStatement.setString(3, "male");
//            preparedStatement.setString(4, "dart");
//            preparedStatement.setString(5, "veider");
//            preparedStatement.setString(6, "imyourfather");
//            preparedStatement.setInt(7, 100);
//            preparedStatement.executeUpdate();
//        }
        clearTable("users");


   }


}
