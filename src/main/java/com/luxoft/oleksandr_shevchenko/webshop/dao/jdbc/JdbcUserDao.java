package com.luxoft.oleksandr_shevchenko.webshop.dao.jdbc;

import com.luxoft.oleksandr_shevchenko.webshop.dao.UserDao;
import com.luxoft.oleksandr_shevchenko.webshop.entity.User;
import java.sql.*;
import java.util.Objects;

public class JdbcUserDao implements UserDao {
    static String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
    static String jdbcUser = "postgres";
    static String jdbcPass = "postgres";
    static String tableName = "users";

    private static final String ADD = "INSERT INTO users (email, password, gender, firstName, lastName, about, age) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String FIND_ALL_SQL = "SELECT id, email, password, gender, firstName, lastName, about, age FROM users;";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?;";
    private static final String UPDATE_BY_ID_PASS = "UPDATE users SET email = ?, password = ? WHERE id = ?;";
    private static final String UPDATE_BY_ID_DATA = "UPDATE users SET gender = ?, firstName = ?, lastName = ?, about = ?, age = ? WHERE id = ?;";
    private static final String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM users WHERE email = ?";

    protected static Connection connect() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }

    @Override
    public void remove(int id) {
        try (Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void edit(User user) {
        editEmailAndPassword(user);
        editInfo(user);
    }


    public void editEmailAndPassword(User user) {
        try (Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_PASS)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void editInfo(User user) {
        try (Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID_DATA)) {
            preparedStatement.setString(1, user.getGender());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getAbout());
            preparedStatement.setInt(5, user.getAge());
            preparedStatement.setInt(6, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(User user) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public User usrFindById(int usrId) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
             preparedStatement.setInt(1, usrId);
             ResultSet resultSet = preparedStatement.executeQuery();
             if (resultSet.next()) {
                 return buildUser(resultSet);
             }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public User findByEmail(String usrEmail) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL)) {
             preparedStatement.setString(1, usrEmail);
             ResultSet resultSet = preparedStatement.executeQuery();
             if (resultSet.next()) {
                return buildUser(resultSet);
             }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public boolean isUserExist(String email) {
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
            throw new RuntimeException(e);
        }
        return false;
    }



    private User buildUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String email = resultSet.getString(2);
        String password = resultSet.getString(3);
        String gender = resultSet.getString(4);
        String firstName = resultSet.getString(5);
        String lastName = resultSet.getString(6);
        String about = resultSet.getString(7);
        int age = resultSet.getInt(8);
        User user = User.builder().
                id(id)
                .email(email)
                .password(password)
                .gender(gender)
                .firstName(firstName)
                .lastName(lastName)
                .about(about)
                .age(age)
                .build();
        return user;
    }

}
