package com.engrkirky.moonlight.util;

public class LocationUtil {
    public static boolean isValidLatitude(double latitude) {
        return latitude >= -90 && latitude <= 90;
    }

    public static boolean isValidLongitude(double longitude) {
        return longitude >= -180 && longitude <= 180;
    }
}
