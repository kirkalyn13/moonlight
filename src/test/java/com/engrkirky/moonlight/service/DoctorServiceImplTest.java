package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.mapper.DoctorMapper;
import com.engrkirky.moonlight.model.Doctor;
import com.engrkirky.moonlight.repository.DoctorRepository;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {
    @Mock private DoctorRepository doctorRepository;
    @Mock private DoctorMapper doctorMapper;
    @InjectMocks private DoctorServiceImpl underTest;
    DoctorDTO doctorDTO1 = new DoctorDTO(
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
    DoctorDTO doctorDTO2 = new DoctorDTO(
            1,
            "bruce.banner",
            "password123",
            "Bruce",
            "Banner",
            120.58865,
            15.16929,
            "0223355779",
            "bruce.banner@test.com",
            false,
            5.00);
    Doctor doctor1 = Doctor.builder()
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
    Doctor doctor2 = Doctor.builder()
            .id(1)
            .username("bruce.banner")
            .firstName("Bruce")
            .lastName("Banner")
            .longitude(120.58865)
            .latitude(15.16929)
            .contactNumber("0223355779")
            .email("bruce.banner@test.com")
            .isAvailable(false)
            .preferredDistance(5.00)
            .build();

    @BeforeEach
    void setUp() {
        underTest = new DoctorServiceImpl(doctorRepository, doctorMapper);
    }

    @Test
    void canGetDoctors() {
        Pageable pageable = PageRequest.of(0, 10);
        String searchString = Strings.EMPTY;
        Page<Doctor> doctorPage = new PageImpl<>(Arrays.asList(doctor1, doctor2));

        underTest.getDoctors(searchString, pageable);

        verify(doctorRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchString, searchString, pageable);
    }

    @Test
    void canGetDoctorById() {
        Integer id = 1;

        underTest.getDoctorById(id);

        verify(doctorRepository).findById(id);
    }

    @Test
    void canGetAvailableDoctors() {
        underTest.getAvailableDoctors();

        verify(doctorRepository).findByIsAvailableTrue();
    }

    @Test
    void canAddDoctor() {
        underTest.addDoctor(doctorDTO1);
        ArgumentCaptor<Doctor> doctorArgumentCaptor = ArgumentCaptor.forClass(Doctor.class);

        verify(doctorRepository).save(doctorArgumentCaptor.capture());

        Doctor capturedDoctor = doctorArgumentCaptor.getValue();
        assertEquals(capturedDoctor, doctor1);
    }

    @Test
    void willThrowWhenDoctorExists() {
        doctorRepository.save(doctor1);

        assertThrows(HttpClientErrorException.Conflict.class, () -> underTest.addDoctor(doctorDTO1));
    }

    @Test
    void updateDoctor() {
        underTest.updateDoctor(doctorDTO1.id(), doctorDTO1);

        verify(doctorRepository).save(doctor1);
    }

    @Test
    void deleteDoctor() {
        underTest.deleteDoctor(doctorDTO1.id());

        verify(doctorRepository).deleteById(doctorDTO1.id());
    }
}