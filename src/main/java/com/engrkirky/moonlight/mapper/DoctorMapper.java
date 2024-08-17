package com.engrkirky.moonlight.mapper;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.model.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DoctorDTO convertToDTO(Doctor entity) {
        return new DoctorDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getLongitude(),
                entity.getLatitude(),
                entity.getContactNumber(),
                entity.getEmail(),
                entity.isAvailable()
        );
    }

    public Doctor convertToEntity(DoctorDTO dto) {
        return modelMapper.map(dto, Doctor.class);
    }
}
