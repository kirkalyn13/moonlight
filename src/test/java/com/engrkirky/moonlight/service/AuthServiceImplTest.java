package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.auth.AuthenticationRequest;
import com.engrkirky.moonlight.auth.AuthenticationResponse;
import com.engrkirky.moonlight.auth.jwt.JwtUtil;
import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.mapper.DoctorMapper;
import com.engrkirky.moonlight.model.Doctor;
import com.engrkirky.moonlight.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock private DoctorRepository doctorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock private AuthenticationManager authenticationManager;

    @Mock private DoctorMapper doctorMapper;

    @InjectMocks private AuthServiceImpl underTest;

    DoctorDTO request = new DoctorDTO(
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
    Doctor doctor = Doctor.builder()
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
    @Test
    void canRegister() {
        when(doctorMapper.convertToEntity(request)).thenReturn(doctor);
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        when(doctorRepository.findByUsername(request.username())).thenReturn(Optional.empty());
        when(jwtUtil.generateToken(doctor)).thenReturn("token");

        AuthenticationResponse response = underTest.register(request);

        assertNotNull(response);
        assertEquals("token", response.token());
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void willThrowWhenRegisteredDoctorExists() {
        when(doctorRepository.findByUsername(request.username())).thenReturn(Optional.of(new Doctor()));

        assertThrows(HttpClientErrorException.class, () -> underTest.register(request));
        verify(doctorRepository, never()).save(any(Doctor.class));

    }

    @Test
    void canAuthenticate() {
        AuthenticationRequest authRequest = new AuthenticationRequest(request.username(), request.password());
        when(doctorRepository.findByUsername(doctor.getUsername())).thenReturn(Optional.of(doctor));
        when(jwtUtil.generateToken(doctor)).thenReturn("token");

        AuthenticationResponse response = underTest.authenticate(authRequest);

        assertNotNull(response);
        assertEquals("token", response.token());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void willThrowWhenAuthenticateInvalidUsername() {
        AuthenticationRequest authRequest = new AuthenticationRequest(request.username(), request.password());

        when(doctorRepository.findByUsername(authRequest.username())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            underTest.authenticate(authRequest);
        });

        assertEquals("User not found.", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}