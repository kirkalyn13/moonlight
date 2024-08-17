package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.dto.DoctorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorService {
    Page<DoctorDTO> getDoctors(String search, Pageable pageable);
    DoctorDTO getDoctorById(Integer id);
    List<DoctorDTO> getAvailableDoctors();
    Integer addDoctor(DoctorDTO doctorDTO);
    DoctorDTO updateDoctor(Integer id, DoctorDTO doctorDTO);
    void deleteDoctor(Integer id);
}
