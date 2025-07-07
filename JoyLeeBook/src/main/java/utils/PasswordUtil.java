package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    /**
     * Hashes a plain-text password using SHA-1.
     *
     * @param password The plain-text password to hash.
     * @return The SHA-1 hashed password in hexadecimal format.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b)); // chuyển byte sang hex
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Thuật toán SHA-1 không hỗ trợ", e);
        }
    }

    /**
     * Verifies a plain-text password against a SHA-1 hashed password.
     *
     * @param plainPassword  The plain-text password.
     * @param hashedPassword The hashed password to compare with.
     * @return true if the hashed version of plainPassword equals hashedPassword.
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return hashPassword(plainPassword).equals(hashedPassword);
    }
}
