package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (" +
            "id INT AUTO_INCREMENT NOT NULL, " +
            "name VARCHAR(30) NOT NULL, " +
            "lastname VARCHAR(30) NOT NULL," +
            "age INT NOT NULL," +
            "PRIMARY KEY(id)" +
            ");";
    private static final String DROP_TABLE_USERS = "DROP TABLE users;";
    private static final String GET_ALL_USERS = "SELECT * FROM users;";
    private static final String ADD_USER = "INSERT INTO users (name, lastname, age) value (?, ?, ?);";
    private static final String REMOVE_USER_BY_ID = "DELETE FROM users WHERE id = ?;";
    private static final String REMOVE_ALL_USERS = "DELETE FROM users;";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_USERS);
        } catch (SQLException throwable) {
            /*NOP*/
        }
    }

    public void dropUsersTable() {
        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(DROP_TABLE_USERS);
        } catch (SQLException throwable) {
            /*NOP*/
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getConnection();
             PreparedStatement statement = conn.prepareStatement(ADD_USER)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            /*NOP*/
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getConnection();
             PreparedStatement statement = conn.prepareStatement(REMOVE_USER_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_USERS)) {
            List<User> listAllUsers = new LinkedList<>();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
                listAllUsers.add(user);
            }
            return listAllUsers;
        } catch (SQLException throwables) {
            return new ArrayList<>();
        }
    }

    public void cleanUsersTable() {
        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(REMOVE_ALL_USERS);
        } catch (SQLException throwables) {
            /*NOP*/
        }
    }
}
