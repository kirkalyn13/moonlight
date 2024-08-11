package com.engrkirky.moonlight.services;

import com.engrkirky.moonlight.dto.DoctorDTO;

import java.util.List;

public interface DoctorService {
    List<DoctorDTO> getDoctors();
    DoctorDTO getDoctorByID(Integer id);
    Integer addDoctor(DoctorDTO doctorDTO);
    DoctorDTO editDoctor(DoctorDTO doctorDTO);
    void deleteDoctor(Integer id);
}
