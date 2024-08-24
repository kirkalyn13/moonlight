package com.engrkirky.moonlight.repository;

import com.engrkirky.moonlight.model.Doctor;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional
class DoctorRepositoryTest {
    @Autowired
    private DoctorRepository underTest;

    @BeforeEach
    void setUp() {
        Doctor doctor1 = Doctor.builder()
                .id(1)
                .username("stephen.strange")
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
        underTest.save(doctor1);
        underTest.save(doctor2);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void canFindDoctors() {
        Pageable pageable = PageRequest.of(0, 10);
        String searchString = Strings.EMPTY;

        Page<Doctor> result = underTest
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchString, searchString, pageable);

        assertEquals(result.getTotalElements(), 2);
    }

    @Test
    void canFindByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Doctor> result =  underTest
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("Steph", "Str", pageable);

        assertEquals(result.getContent().get(0).getFirstName(), "Stephen");
        assertEquals(result.getContent().get(0).getLastName(), "Strange");
    }

    @Test
    void canFindByNoFirstNameContainingIgnoreCaseOrNoLastNameContainingIgnoreCase() {
        Pageable pageable = PageRequest.of(0, 1);

        Page<Doctor> result =  underTest
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("None", "None", pageable);

        assertEquals(result.getTotalElements(), 0);
    }

    @Test
    void canFindByIsAvailableTrue() {
        List<Doctor> results =  underTest.findByIsAvailableTrue();

        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getUsername(), "stephen.strange");
    }


    @Test
    void canFindByUsername() {
        Optional<Doctor> result =  underTest.findByUsername("stephen.strange");

        assertTrue(result.isPresent());
        assertEquals(result.get().getUsername(), "stephen.strange");
    }
}