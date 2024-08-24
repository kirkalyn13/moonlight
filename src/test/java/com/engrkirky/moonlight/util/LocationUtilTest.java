package com.engrkirky.moonlight.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationUtilTest {

    @Test
    void shouldBeValidLatitude() {
        assertTrue(LocationUtil.isValidLatitude(0.0));
        assertTrue(LocationUtil.isValidLatitude(-90.0));
        assertTrue(LocationUtil.isValidLatitude(90.0));
        assertTrue(LocationUtil.isValidLatitude(45.0));
    }

    @Test
    void shouldBeInvalidLatitude() {
        assertFalse(LocationUtil.isValidLatitude(-91.0));
        assertFalse(LocationUtil.isValidLatitude(91.0));
    }

    @Test
    void shouldBeValidLongitude() {
        assertTrue(LocationUtil.isValidLongitude(0.0));
        assertTrue(LocationUtil.isValidLongitude(-180.0));
        assertTrue(LocationUtil.isValidLongitude(180.0));
        assertTrue(LocationUtil.isValidLongitude(45.0));
    }

    @Test
    void shouldBeInvalidLongitude() {
        assertFalse(LocationUtil.isValidLongitude(-181.0));
        assertFalse(LocationUtil.isValidLongitude(181.0));
    }

    @Test
    void canCalculateZeroDistance() {
        double distance = LocationUtil.calculateDistance(0.0, 0.0, 0.0, 0.0);
        assertEquals(0.0, distance, 0.0001);
    }

    @Test
    void canCalculateDistance() {
        double lon1 = 0.0;
        double lat1 = 0.0;
        double lon2 = 90.0;
        double lat2 = 0.0;

        double distance = LocationUtil.calculateDistance(lon1, lat1, lon2, lat2);

        assertEquals(10007.543, distance, 0.1);
    }

    @Test
    void canCalculateDistanceForEdgeCases() {
        double distance = LocationUtil.calculateDistance(0.0, -90.0, 0.0, 90.0);
        assertEquals(20015.086, distance, 0.1);
    }
}