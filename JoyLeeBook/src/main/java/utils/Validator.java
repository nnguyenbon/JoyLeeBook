package utils;

public class Validator {

    /**
     * Validates if the given string is not null or empty.
     *
     * @param value the string to validate
     * @return true if the string is valid, false otherwise
     */
    public static boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Validates if the given integer is not null and greater than zero.
     *
     * @param value the integer to validate
     * @return true if the integer is valid, false otherwise
     */
    public static boolean isValidInteger(Integer value) {
        return value != null && value > 0;
    }

    /**
     * Validates if the given email is in a valid format.
     *
     * @param email the email to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}