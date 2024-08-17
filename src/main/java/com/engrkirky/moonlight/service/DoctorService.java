package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.dto.DoctorDTO;

import java.util.List;

public interface DoctorService {
    List<DoctorDTO> getDoctors();
    DoctorDTO getDoctorById(Integer id);
    List<DoctorDTO> getAvailableDoctors();
    Integer addDoctor(DoctorDTO doctorDTO);
    DoctorDTO updateDoctor(Integer id, DoctorDTO doctorDTO);
    void deleteDoctor(Integer id);
}
