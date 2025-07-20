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
    public static boolean isValidInteger(String value) {
        try {
            return Integer.parseInt(value) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
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

    /**
     * Validates if the given phone is in a valid format.
     *
     * @param phone the phone number to validate
     * @return true if the phone number is valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^\\d{10,11}$"; // 10–11 digits
        return phone != null && phone.matches(phoneRegex);
    }

    public static boolean isValidPassword(String password) {
        if (password == null) return false;

        // Check password length
        if (password.length() < 8) return false;

        // Regex breakdown:
        // (?=.*[A-Z])        → at least one uppercase letter
        // (?=.*[a-zA-Z])     → at least one letter (uppercase or lowercase)
        // (?=.*\\d)          → at least one digit
        // (?=.*[!@#$%^&*()_+=<>?{}\\[\\]-]) → at least one special character
        // .{8,}              → at least 8 characters (redundant if checked earlier)

        String regex = "^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=<>?{}\\[\\]-]).{8,}$";

        return password.matches(regex);
    }
}