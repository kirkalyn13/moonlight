package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.auth.AuthenticationRequest;
import com.engrkirky.moonlight.auth.AuthenticationResponse;
import com.engrkirky.moonlight.auth.jwt.JwtUtil;
import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.mapper.DoctorMapper;
import com.engrkirky.moonlight.model.Doctor;
import com.engrkirky.moonlight.repository.DoctorRepository;
import com.engrkirky.moonlight.util.Constants;
import com.engrkirky.moonlight.util.DoctorUtil;
import com.engrkirky.moonlight.util.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class AuthServiceImpl implements AuthService {
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final DoctorMapper doctorMapper;

    @Autowired
    public AuthServiceImpl(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.doctorMapper = doctorMapper;
    }

    @Override
    public AuthenticationResponse register(DoctorDTO doctorDTO) {
        if (!validateDoctor(doctorDTO)) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        if (doctorRepository.findByUsername(doctorDTO.username()).isPresent()) throw new HttpClientErrorException((HttpStatus.CONFLICT));

        Doctor doctor = doctorMapper.convertToEntity(doctorDTO);
        doctor.setPassword(passwordEncoder.encode(doctorDTO.password()));

        doctorRepository.save(doctor);
        String jwtToken = jwtUtil.generateToken(doctor);

        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        Doctor doctor = doctorRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        String jwtToken = jwtUtil.generateToken(doctor);

        return new AuthenticationResponse(jwtToken);
    }

    private static boolean validateDoctor(DoctorDTO doctorDTO) {
        if (doctorDTO.username() == null || !DoctorUtil.isValidUserName(doctorDTO.username())) return false;
        if (doctorDTO.password() == null || !DoctorUtil.isValidPassword(doctorDTO.password())) return false;
        if (doctorDTO.firstName() == null) return false;
        if (doctorDTO.lastName() == null) return false;
        if (!LocationUtil.isValidLongitude(doctorDTO.longitude()) || doctorDTO.longitude() == Constants.EMPTY_DOUBLE_FIELD) return false;
        if (!LocationUtil.isValidLatitude(doctorDTO.latitude()) || doctorDTO.latitude() == Constants.EMPTY_DOUBLE_FIELD) return false;
        if (doctorDTO.contactNumber() == null || !DoctorUtil.isValidContactNumber(doctorDTO.contactNumber())) return false;
        if (doctorDTO.email() == null || !DoctorUtil.isValidEmail(doctorDTO.email())) return false;
        return doctorDTO.preferredDistance() != Constants.EMPTY_DOUBLE_FIELD;
    }
}
