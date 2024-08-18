package com.engrkirky.moonlight.mapper;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.model.Doctor;

public interface DoctorMapper {
    DoctorDTO convertToDTO(Doctor doctor);
    Doctor convertToEntity(DoctorDTO doctorDTO);
}
