package com.engrkirky.moonlight.mapper;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.model.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapperImpl implements DoctorMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorDTO convertToDTO(Doctor doctor) {
        return new DoctorDTO(
                doctor.getId(),
                doctor.getUsername(),
                doctor.getPassword(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getLongitude(),
                doctor.getLatitude(),
                doctor.getContactNumber(),
                doctor.getEmail(),
                doctor.isAvailable()
        );
    }

    @Override
    public Doctor convertToEntity(DoctorDTO DoctorDTO) {
        return modelMapper.map(DoctorDTO, Doctor.class);
    }
}
