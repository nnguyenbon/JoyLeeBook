package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    /**
     * Hashes a plain-text password using BCrypt.
     * BCrypt automatically generates a unique salt and includes it in the hash.
     *
     * @param password The plain-text password to hash.
     * @return The BCrypt hashed password string.
     */
    public static String hashPassword(String password) {
        // BCrypt.gensalt() generates a unique salt for each password.
        // The '12' is the 'log rounds' or 'work factor'.
        // Higher values mean more CPU cycles are used, making it slower but more secure against brute-force attacks.
        // A value of 10-12 is generally recommended.
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Verifies a plain-text password against a BCrypt hashed password.
     * BCrypt automatically extracts the salt from the hashed password for verification.
     *
     * @param plainPassword  The plain-text password provided by the user.
     * @param hashedPassword The stored BCrypt hashed password (obtained from hashPassword method).
     * @return true if the passwords match, false otherwise.
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        // BCrypt.checkpw() handles the hashing of the plainPassword with the salt
        // extracted from the hashedPassword, then compares them securely.
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /**
     * Serves as a debug and demonstration entry point for the password hashing methods.
     *
     * @param args Command-line arguments (not used in this example).
     */
    public static void main(String[] args) {
        // --- Example Usage ---
        String passwordToHash = "admin";
        String hashedPassword = hashPassword(passwordToHash);
        System.out.println("Hashed password for '" + passwordToHash + "': " + hashedPassword);
        System.out.println("----------------------------------------");

        // --- Verification Test ---
        // Check if a given plaintext password matches the generated hash.
        // Note: The original example incorrectly checked "admin". This has been corrected
        // to use the actual password that was hashed.
        String correctPassword = "admin";
        String incorrectPassword = "wrongpassword";

        System.out.println("Checking '" + correctPassword + "': " + checkPassword(correctPassword, hashedPassword)); // Should return true
        System.out.println("Checking '" + incorrectPassword + "': " + checkPassword(incorrectPassword, hashedPassword)); // Should return false
        System.out.println("----------------------------------------");

        // --- Salting Demonstration ---
        // Demonstrate that hashing the same password multiple times produces a different
        // hash each time. This is a key security feature of BCrypt, as the salt is
        // automatically generated and included in the hash.
        System.out.println("Generating another hash for the same password ('" + passwordToHash + "')...");
        System.out.println("New hash: " + hashPassword(passwordToHash));
        System.out.println("Notice the new hash is different from the first one.");
    }
}