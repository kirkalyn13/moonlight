package com.engrkirky.moonlight.mapper;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoctorMapperImplTest {
    private DoctorMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new DoctorMapperImpl();
    }

    DoctorDTO dto = new DoctorDTO(
            1,
            "stephen.strange",
            "password123",
            "Stephen",
            "Strange",
            120.58865,
            15.16929,
            "0123456789",
            "stephen.strange@test.com",
            true,
            10.0);

    Doctor entity = Doctor.builder()
            .id(1)
            .username("stephen.strange")
            .password("password123")
            .firstName("Stephen")
            .lastName("Strange")
            .longitude(120.58865)
            .latitude(15.16929)
            .contactNumber("0123456789")
            .email("stephen.strange@test.com")
            .isAvailable(true)
            .preferredDistance(10.00)
            .build();
    @Test
    void canConvertToDTO() {
        DoctorDTO result = underTest.convertToDTO(entity);
        assertEquals(result, dto);
    }

    @Test
    void canConvertToEntity() {
        Doctor result = underTest.convertToEntity(dto);
        assertEquals(result, entity);
    }
}