/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

/**
 * DAO class for handling database operations related to User entity. Provides
 * methods to insert, update, delete, and retrieve Users records from the
 * database.
 * Assumes the Users table has columns: user_id (PK), role_name, username,
 * email, password.
 *
 * @author Trunguyen
 */
public class UserDAO {

    private final Connection connection;

    /**
     * Constructor to initialize UserDAO with a database connection.
     *
     * @param connection Active SQL connection to be used in DAO methods.
     */
    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Get all users from the database.
     *
     * @return List of all users.
     * @throws java.sql.SQLException
     */
    public List<User> getAllUser() throws SQLException {
        List<User> listUser = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                listUser.add(extractUserFromResultSet(rs));
            }
        }
        return listUser;
    }

    /**
     * Get a user by their ID.
     *
     * @param user_id User ID
     * @return User object if found, otherwise null.
     * @throws java.sql.SQLException
     */
    public User getUserById(int user_id) throws SQLException {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, user_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        }
        return null;
    }

    /**
     * Insert a new user into the database.
     *
     * @param user The User object to insert.
     * @return true if insert is successful, false otherwise.
     * @throws java.sql.SQLException
     */
    public boolean insertUser(User user) throws SQLException {
        String sql = "INSERT INTO Users(role_name, username, email, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getRoleName());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Update an existing user's information.
     *
     * @param user The User object with updated data.
     * @return true if update is successful, false otherwise.
     * @throws java.sql.SQLException
     */
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET role_name = ?, username = ?, email = ?, password = ? WHERE user_id = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getRoleName());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setInt(5, user.getUserId());
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Delete a user from the database by ID.
     *
     * @param user_id The user ID to delete.
     * @return true if delete is successful, false otherwise.
     * @throws java.sql.SQLException
     */
    public boolean deleteUser(int user_id) throws SQLException {
        String sql = "DELETE FROM Users WHERE user_id = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, user_id);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Check user login credentials.
     *
     * @param username username
     * @param password password
     * @return User object if login is successful, otherwise null.
     * @throws java.sql.SQLException
     */
    public User checkLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        }
        return null;
    }

    /**
     * Checks if a username is a duplicate in the database.
     *
     * @param username The username to check for duplicates.
     * @return true if the username already exists, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean isDuplicateUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Checks if an email is a duplicate in the database.
     *
     * @param email The email to check for duplicates.
     * @return true if the email already exists, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean isDuplicateEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Helper method to build a User object from ResultSet.
     *
     * @param rs ResultSet from SQL query
     * @return User object
     * @throws SQLException if any column access fails
     */
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setRoleName(rs.getString("role_name"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}
