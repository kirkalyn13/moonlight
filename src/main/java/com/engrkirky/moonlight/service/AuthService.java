package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.auth.AuthenticationRequest;
import com.engrkirky.moonlight.auth.AuthenticationResponse;
import com.engrkirky.moonlight.dto.DoctorDTO;

public interface AuthService {
    AuthenticationResponse register(DoctorDTO doctorDTO);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
