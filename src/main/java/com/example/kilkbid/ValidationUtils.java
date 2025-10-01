package com.example.kilkbid;

import java.util.regex.Pattern;

public class ValidationUtils {

    // Toggle between strict/relaxed rules
    public static final boolean STRICT_MODE = false;

    // Name patterns
    private static final Pattern STRICT_NAME = Pattern.compile("^[A-Za-z]{2,30}$");
    private static final Pattern RELAXED_NAME = Pattern.compile("^[A-Za-z ]{2,50}$");

    // Email patterns
    private static final Pattern STRICT_EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@gmail\\.com$");
    private static final Pattern RELAXED_EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Password patterns
    private static final Pattern STRICT_PASSWORD =
            Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    private static final Pattern RELAXED_PASSWORD =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).{6,}$");

    public static boolean isValidName(String name) {
        return STRICT_MODE ? STRICT_NAME.matcher(name).matches()
                : RELAXED_NAME.matcher(name).matches();
    }

    public static boolean isValidEmail(String email) {
        return STRICT_MODE ? STRICT_EMAIL.matcher(email).matches()
                : RELAXED_EMAIL.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return STRICT_MODE ? STRICT_PASSWORD.matcher(password).matches()
                : RELAXED_PASSWORD.matcher(password).matches();
    }
}
