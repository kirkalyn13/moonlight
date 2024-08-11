package com.engrkirky.moonlight.services;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.dto.DoctorLocationDTO;
import com.engrkirky.moonlight.model.Doctor;
import com.engrkirky.moonlight.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Integer addDoctor(DoctorDTO doctorDTO) {
        Doctor newDoctor = doctorRepository.save(convertToEntity(doctorDTO));
        return newDoctor.getId();
    }

    @Override
    public DoctorDTO editDoctor(Integer id, DoctorDTO doctorDTO) {
        Doctor editedDoctor = doctorRepository.save(convertToEntity(doctorDTO));
        return convertToDTO(editedDoctor);
    }

    @Override
    public void deleteDoctor(Integer id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public DoctorLocationDTO updateDoctorLocation(Integer id, DoctorLocationDTO doctorLocationDTO) {
        return doctorLocationDTO;
    }

    private DoctorDTO convertToDTO(Doctor entity) {
        return modelMapper.map(entity, DoctorDTO.class);
    }

    private Doctor convertToEntity(DoctorDTO dto) {
        return modelMapper.map(dto, Doctor.class);
    }
}
