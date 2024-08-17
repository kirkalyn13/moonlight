package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.model.Doctor;
import com.engrkirky.moonlight.repository.DoctorRepository;
import com.engrkirky.moonlight.util.DoctorUtil;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<DoctorDTO> getDoctors() {
        return doctorRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public DoctorDTO getDoctorById(Integer id) {
        return convertToDTO(doctorRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Integer addDoctor(DoctorDTO doctorDTO) {
        Doctor newDoctor = doctorRepository.save(convertToEntity(doctorDTO));
        return newDoctor.getId();
    }

    @Override
    @Transactional
    public DoctorDTO updateDoctor(Integer id, DoctorDTO doctorDTO) {
        return doctorRepository
                .findById(id)
                .map(doctor -> {
                    if (doctorDTO.getUsername() != null && DoctorUtil.isValidUserName(doctorDTO.getUsername())) {
                        doctor.setUsername(doctorDTO.getUsername());
                    }
                    if (doctorDTO.getPassword() != null && DoctorUtil.isValidPassword(doctorDTO.getPassword())) {
                        doctor.setPassword(doctorDTO.getPassword());
                    }
                    if (doctorDTO.getFirstName() != null) {
                        doctor.setFirstName(doctorDTO.getFirstName());
                    }
                    if (doctorDTO.getLastName() != null) {
                        doctor.setLastName(doctorDTO.getLastName());
                    }
                    if (DoctorUtil.isValidLongitude(doctorDTO.getLongitude())) {
                        doctor.setLongitude(doctorDTO.getLongitude());
                    }
                    if (DoctorUtil.isValidLatitude(doctorDTO.getLatitude())) {
                        doctor.setLatitude(doctorDTO.getLatitude());
                    }
                    if (doctorDTO.getContactNumber() != null) {
                        doctor.setContactNumber(doctorDTO.getContactNumber());
                    }
                    if (doctorDTO.getEmail() != null && DoctorUtil.isValidEmail(doctorDTO.getEmail())) {
                        doctor.setEmail(doctorDTO.getEmail());
                    }
                    if (doctorDTO.isAvailable()) {
                        doctor.setAvailable(true);
                    }
                    return convertToDTO(doctorRepository.save(doctor));
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("Doctor with ID of %d not found.", id)));
    }

    @Override
    @Transactional
    public void deleteDoctor(Integer id) {
        doctorRepository
                .findById(id)
                .map(doctor -> {
                    doctorRepository.deleteById(doctor.getId());
                    return doctor;
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("Doctor with ID of %d not found.", id)));
    }

    private DoctorDTO convertToDTO(Doctor entity) {
        return modelMapper.map(entity, DoctorDTO.class);
    }

    private Doctor convertToEntity(DoctorDTO dto) {
        return modelMapper.map(dto, Doctor.class);
    }
}
