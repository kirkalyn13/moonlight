package com.engrkirky.moonlight.services;

import com.engrkirky.moonlight.dto.DoctorDTO;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface DoctorService {
    List<DoctorDTO> getDoctors();
    DoctorDTO getDoctorById(Integer id);
    Integer addDoctor(DoctorDTO doctorDTO);
    DoctorDTO editDoctor(Integer id, DoctorDTO doctorDTO);
    void deleteDoctor(Integer id);
}
