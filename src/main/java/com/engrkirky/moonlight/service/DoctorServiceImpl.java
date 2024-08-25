package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.mapper.DoctorMapper;
import com.engrkirky.moonlight.repository.DoctorRepository;
import com.engrkirky.moonlight.util.Constants;
import com.engrkirky.moonlight.util.DoctorUtil;
import com.engrkirky.moonlight.util.LocationUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }


    @Override
    public Page<DoctorDTO> getDoctors(String search, Pageable pageable) {
        return doctorRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(search, search, pageable)
                .map(doctorMapper::convertToDTO);
    }

    @Override
    public DoctorDTO getDoctorById(Integer id) {
        return doctorMapper.convertToDTO(doctorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Doctor with ID of %d not found.", id))));
    }

    @Override
    public List<DoctorDTO> getAvailableDoctors() {
        return doctorRepository
                .findByIsAvailableTrue()
                .stream()
                .map(doctorMapper::convertToDTO)
                .toList();
    }

    @Override
    @Transactional
    public DoctorDTO updateDoctor(Integer id, DoctorDTO doctorDTO) {
        return doctorRepository
                .findById(id)
                .map(doctor -> {
                    if (doctorDTO.username() != null && DoctorUtil.isValidUserName(doctorDTO.username())) doctor.setUsername(doctorDTO.username());
                    if (doctorDTO.password() != null && DoctorUtil.isValidPassword(doctorDTO.password())) doctor.setPassword(doctorDTO.password());
                    if (doctorDTO.firstName() != null) doctor.setFirstName(doctorDTO.firstName());
                    if (doctorDTO.lastName() != null) doctor.setLastName(doctorDTO.lastName());
                    if (LocationUtil.isValidLongitude(doctorDTO.longitude()) && doctorDTO.longitude() != Constants.EMPTY_DOUBLE_FIELD) doctor.setLongitude(doctorDTO.longitude());
                    if (LocationUtil.isValidLatitude(doctorDTO.latitude()) && doctorDTO.latitude() != Constants.EMPTY_DOUBLE_FIELD) doctor.setLatitude(doctorDTO.latitude());
                    if (doctorDTO.contactNumber() != null && DoctorUtil.isValidContactNumber(doctorDTO.contactNumber())) doctor.setContactNumber(doctorDTO.contactNumber());
                    if (doctorDTO.email() != null && DoctorUtil.isValidEmail(doctorDTO.email())) doctor.setEmail(doctorDTO.email());
                    if (doctorDTO.isAvailable()) doctor.setAvailable(true);
                    if (doctorDTO.preferredDistance() != Constants.EMPTY_DOUBLE_FIELD) doctor.setPreferredDistance(doctorDTO.preferredDistance());
                    return doctorMapper.convertToDTO(doctorRepository.save(doctor));
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
}
