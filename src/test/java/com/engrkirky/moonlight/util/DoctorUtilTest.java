package com.engrkirky.moonlight.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoctorUtilTest {

    @Test
    void shouldBeValidUserName() {
        assertTrue(DoctorUtil.isValidUserName("validUsername"));
    }

    @Test
    void shouldNotBeValidUserName() {
        assertFalse(DoctorUtil.isValidUserName("short"));
    }

    @Test
    void shouldBeValidPassword() {
        assertTrue(DoctorUtil.isValidPassword("validPassword"));
    }

    @Test
    void shouldBeInvalidPassword() {
        assertFalse(DoctorUtil.isValidPassword("short"));
    }

    @Test
    void shouldBeValidContactNumber() {
        assertTrue(DoctorUtil.isValidContactNumber("09123456789"));
    }

    @Test
    void shouldBeInvalidContactNumber() {
        assertFalse(DoctorUtil.isValidContactNumber("123456789"));
        assertFalse(DoctorUtil.isValidContactNumber("123456789012"));
        assertFalse(DoctorUtil.isValidContactNumber("abcdefghijk"));
    }

    @Test
    void shouldBeValidEmail() {
        assertTrue(DoctorUtil.isValidEmail("testuser@test.com"));
    }

    @Test
    void shouldBeInvalidEmail() {
        assertFalse(DoctorUtil.isValidEmail("plainaddress"));
        assertFalse(DoctorUtil.isValidEmail("@missingusername.com"));
        assertFalse(DoctorUtil.isValidEmail("username@.missingdomain"));
        assertFalse(DoctorUtil.isValidEmail("username@domain.c"));
        assertFalse(DoctorUtil.isValidEmail(null));
    }
}