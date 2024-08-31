package com.engrkirky.moonlight.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoctorUtil {
    private DoctorUtil() {}
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidUserName(String username) {
        return username.split("").length > 8;
    }

    public static boolean isValidPassword(String username) {
        return username.split("").length > 8;
    }

    public static boolean isValidContactNumber(String input) {
        return input != null && input.matches("\\d{11}");
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
