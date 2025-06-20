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
 * methods to insert, update, delete, and retrieve chapter records from the
 * database.
 *
 * Assumes the Chapter table has columns: user_id (PK), role_name, username,
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

    // /**
    //  * Check user login credentials.
    //  *
    //  * @param username username
    //  * @param password password
    //  * @return User object if login is successful, otherwise null.
    //  */
    // public User checkLogin(String username, String password) throws SQLException {
    //     String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
    //     try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //         pstmt.setString(1, username);
    //         pstmt.setString(2, password);
    //         ResultSet rs = pstmt.executeQuery();
    //         if (rs.next()) {
    //             return extractUserFromResultSet(rs);
    //         }
    //     }
    //     return null;
    // }

    /**
     * Get all users from the database.
     *
     * @return List of all users.
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
     * @param id User ID
     * @return User object if found, otherwise null.
     */
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        }

        return null;
    }

    /**
     * Insert a new user into the database.
     *
     * @param user The User object to insert.
     * @return true if insert is successful, false otherwise.
     */
    public boolean insertUser(User user) throws SQLException {
        String sql = "INSERT INTO Users(role_name, username, email, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(4, user.getRoleName());
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Update an existing user's information.
     *
     * @param user The User object with updated data.
     * @return true if update is successful, false otherwise.
     */
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET role_name = ?, username = ?, email = ?, password = ? WHERE id = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(4, user.getRoleName());
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Delete a user from the database by ID.
     *
     * @param id The user ID to delete.
     * @return true if delete is successful, false otherwise.
     */
    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM Users WHERE id = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
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