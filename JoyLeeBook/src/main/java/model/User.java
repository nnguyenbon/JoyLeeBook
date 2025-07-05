package model;

/**
 * Represents a user in the system.
 * A user has a role, username, email, and password.
 */
public class User {
    private int userId;
    private String roleName;
    private String username;
    private String email;
    private String password;

    /**
     * Default constructor.
     * Creates an empty User object.
     */
    public User() {
    }

    /**
     * Constructs a User object with the specified role, username, email, and password.
     *
     * @param roleName The role assigned to the user (e.g., admin, member).
     * @param username The user's username.
     * @param email The user's email address.
     * @param password The user's password.
     */
    public User(String roleName, String username, String email, String password) {
        this.roleName = roleName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the user's ID.
     *
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user's ID.
     *
     * @param userId The user ID to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the user's role name.
     *
     * @return The role name.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the user's role name.
     *
     * @param roleName The role name to set.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Returns the username.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's email address.
     *
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
