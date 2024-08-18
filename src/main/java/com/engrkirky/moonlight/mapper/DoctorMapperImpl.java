package com.engrkirky.moonlight.mapper;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.model.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapperImpl implements DoctorMapper {
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
    public Doctor convertToEntity(DoctorDTO doctorDTO) {
        return Doctor.builder()
                .id(doctorDTO.id())
                .username(doctorDTO.username())
                .password(doctorDTO.password())
                .firstName(doctorDTO.firstName())
                .lastName(doctorDTO.lastName())
                .longitude(doctorDTO.longitude())
                .latitude(doctorDTO.latitude())
                .contactNumber(doctorDTO.contactNumber())
                .email(doctorDTO.email())
                .isAvailable(doctorDTO.isAvailable())
                .build();
    }
}
