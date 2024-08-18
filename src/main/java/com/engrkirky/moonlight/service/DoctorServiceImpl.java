package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.mapper.DoctorMapper;
import com.engrkirky.moonlight.repository.DoctorRepository;
import com.engrkirky.moonlight.util.DoctorUtil;
import com.engrkirky.moonlight.util.LocationUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

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
    public Integer addDoctor(DoctorDTO doctorDTO) {
        if (!validateDoctor(doctorDTO)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        return doctorRepository.save(doctorMapper.convertToEntity(doctorDTO)).getId();
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
                    if (LocationUtil.isValidLongitude(doctorDTO.longitude())) doctor.setLongitude(doctorDTO.longitude());
                    if (LocationUtil.isValidLatitude(doctorDTO.latitude())) doctor.setLatitude(doctorDTO.latitude());
                    if (doctorDTO.contactNumber() != null && DoctorUtil.isValidContactNumber(doctorDTO.contactNumber())) doctor.setContactNumber(doctorDTO.contactNumber());
                    if (doctorDTO.email() != null && DoctorUtil.isValidEmail(doctorDTO.email())) doctor.setEmail(doctorDTO.email());
                    if (doctorDTO.isAvailable()) doctor.setAvailable(true);
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

    private static boolean validateDoctor(DoctorDTO doctorDTO) {
        if (doctorDTO.username() == null || !DoctorUtil.isValidUserName(doctorDTO.username())) return false;
        if (doctorDTO.password() == null|| !DoctorUtil.isValidPassword(doctorDTO.password())) return false;
        if (doctorDTO.firstName() == null) return false;
        if (doctorDTO.lastName() == null) return false;
        if (!LocationUtil.isValidLongitude(doctorDTO.longitude())) return false;
        if (!LocationUtil.isValidLatitude(doctorDTO.latitude())) return false;
        if (doctorDTO.contactNumber() == null || !DoctorUtil.isValidContactNumber(doctorDTO.contactNumber())) return false;

        return doctorDTO.email() != null && DoctorUtil.isValidEmail(doctorDTO.email());
    }
}
