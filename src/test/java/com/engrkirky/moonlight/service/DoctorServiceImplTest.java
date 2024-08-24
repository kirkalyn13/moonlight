package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.mapper.DoctorMapper;
import com.engrkirky.moonlight.model.Doctor;
import com.engrkirky.moonlight.repository.DoctorRepository;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.AfterEach;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
            "96123456789",
            "stephen.strange@test.com",
            true,
            10.0);
    DoctorDTO doctorDTO2 = new DoctorDTO(
            2,
            "bruce.banner",
            "password123",
            "Bruce",
            "Banner",
            120.58865,
            15.16929,
            "92233557799",
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
            .contactNumber("96123456789")
            .email("stephen.strange@test.com")
            .isAvailable(true)
            .preferredDistance(10.00)
            .build();
    Doctor doctor2 = Doctor.builder()
            .id(2)
            .username("bruce.banner")
            .firstName("Bruce")
            .lastName("Banner")
            .longitude(120.58865)
            .latitude(15.16929)
            .contactNumber("92233557799")
            .email("bruce.banner@test.com")
            .isAvailable(false)
            .preferredDistance(5.00)
            .build();

    @BeforeEach
    void setUp() {
        underTest = new DoctorServiceImpl(doctorRepository, doctorMapper);
    }

    @AfterEach
    void tearDown() {
        doctorRepository.deleteAll();
    }

    @Test
    void canGetDoctors() {
        Pageable pageable = PageRequest.of(0, 10);
        String searchString = Strings.EMPTY;

        when(doctorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchString, searchString, pageable))
                .thenReturn(new PageImpl<>(List.of(doctor1)));
        when(doctorMapper.convertToDTO(doctor1)).thenReturn(doctorDTO1);

        Page<DoctorDTO> result = underTest.getDoctors(searchString, pageable);

        verify(doctorRepository).findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchString, searchString, pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(doctorDTO1, result.getContent().get(0));
    }

    @Test
    void canGetDoctorById() {
        Integer id = 1;

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor1));
        when(doctorMapper.convertToDTO(doctor1)).thenReturn(doctorDTO1);

        DoctorDTO result = underTest.getDoctorById(id);

        verify(doctorRepository).findById(id);
        assertEquals(doctorDTO1, result);
    }

    @Test
    void canGetAvailableDoctors() {
        underTest.getAvailableDoctors();

        verify(doctorRepository).findByIsAvailableTrue();
    }

    @Test
    void canAddDoctor() {
        when(doctorMapper.convertToEntity(doctorDTO1)).thenReturn(doctor1);
        when(doctorRepository.save(doctor1)).thenReturn(doctor1);

        Integer result = underTest.addDoctor(doctorDTO1);

        ArgumentCaptor<Doctor> doctorArgumentCaptor = ArgumentCaptor.forClass(Doctor.class);
        verify(doctorRepository).save(doctorArgumentCaptor.capture());
        assertEquals(doctor1, doctorArgumentCaptor.getValue());
        assertEquals(doctor1.getId(), result);
    }

    @Test
    void willThrowWhenDoctorAddedExists() {
        when(doctorRepository.findByUsername(doctorDTO1.username())).thenReturn(Optional.of(doctor1));

        assertThrows(HttpClientErrorException.class, () -> underTest.addDoctor(doctorDTO1));
    }

    @Test
    void updateDoctor() {
        when(doctorRepository.findById(doctorDTO1.id())).thenReturn(Optional.of(doctor1));
        when(doctorRepository.save(doctor1)).thenReturn(doctor1);
        when(doctorMapper.convertToDTO(doctor1)).thenReturn(doctorDTO1);

        DoctorDTO result = underTest.updateDoctor(doctorDTO1.id(), doctorDTO1);

        verify(doctorRepository).save(doctor1);
        assertEquals(doctorDTO1, result);
    }

    @Test
    void deleteDoctor() {
        when(doctorRepository.findById(doctorDTO1.id())).thenReturn(Optional.of(doctor1));

        underTest.deleteDoctor(doctorDTO1.id());

        verify(doctorRepository).deleteById(doctorDTO1.id());
    }
}